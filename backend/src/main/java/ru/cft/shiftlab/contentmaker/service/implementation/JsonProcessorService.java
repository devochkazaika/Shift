package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.dto.StoryPatchDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationFramesRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationRepository;
import ru.cft.shiftlab.contentmaker.service.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.util.keycloak.KeyCloak;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ru.cft.shiftlab.contentmaker.util.Constants.*;


/**
 * Сервис предназначенный для сохранения JSON файла и картинок.
 */
@Service
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "files.save.directory")
@Log4j2
public class JsonProcessorService implements FileSaverService {
    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature
                        .FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;
    private final DirProcess dirProcess;
    private final StoryPresentationRepository storyPresentationRepository;
    private final StoryPresentationFramesRepository storyPresentationFramesRepository;

    /**
     * Метод возвращает все истории в виде списка
     */
    private List<StoryPresentation> getStoryList(String bankId, String platform) throws IOException {
        List<StoryPresentation> list = dirProcess.checkFileInBankDir(
                FileNameCreator.createJsonName(bankId, platform),
                STORIES);
        return (list == null) ? new ArrayList<StoryPresentation>() : list;
    }
    /**
     * Метод возвращает конкретную историю
     */
    private StoryPresentation getStoryModel(List<StoryPresentation> storyPresentationList,
                                            Long id) throws IOException {
        return storyPresentationList.stream()
                .filter(x-> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find the story with id=" + id));
    }

    public List<StoryPresentation> getUnApprovedStories(String bankId, String platform){
        return storyPresentationRepository.getUnApprovedStories(bankId, platform);
    }

    public HttpEntity<List<StoryPresentation>> getFilePlatformJson(String bankId, String platform) throws IOException {
        return new HttpEntity<>(getStoryList(bankId, platform));
    }

    @Override
    public void saveFiles(String strStoriesRequestDto,
                          MultipartFile previewImage,
                          MultipartFile[] images){
        try {
            StoriesRequestDto storiesRequestDto = mapper.readValue(
                    mapper.readValue(strStoriesRequestDto, String.class)
                    , StoriesRequestDto.class);
            String bankId = storiesRequestDto.getBankId();
            String platformType = storiesRequestDto.getPlatformType();

            //Создание пути для картинок, если его еще нет
            String picturesSaveDirectory = FILES_SAVE_DIRECTORY+bankId+"/"+platformType+"/";
            dirProcess.createFolders(picturesSaveDirectory);

            //Преобразование из dto в entity с сохранением картинок
            LinkedList<MultipartFile> imageQueue = Arrays.stream(images).
                    collect(Collectors.toCollection(LinkedList::new));
            imageQueue.addFirst(previewImage);
            StoryPresentation storyPresentation = dtoToEntityConverter.fromStoryDtoToStoryPresentation(bankId, platformType, storiesRequestDto.getStoryDtos().get(0), imageQueue);

            //Сохранение
            saveToJsonByRoles(storyPresentation, bankId, platformType);
        }
        catch (JsonProcessingException e){
            throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        catch (IOException e) {
            throw new StaticContentException("Could not save files", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
    }

    /**
     * Сохранение истории в зависимости от роли
     * @param storyPresentation
     * @param bankId
     * @param platformType
     * @throws IOException
     */
    public void saveToJsonByRoles(StoryPresentation storyPresentation, String bankId, String platformType) throws IOException {
        Set<KeyCloak.Roles> roles = KeyCloak.getRoles();
        List<StoryPresentation> storyPresentationList = getStoryList(bankId, platformType);
        storyPresentationList.add(storyPresentation);
        if (roles.contains(KeyCloak.Roles.ADMIN)){
            putStoryToJson(storyPresentationList, bankId, platformType);
            storyPresentation.setApproved(true);
        }
        else if (roles.contains(KeyCloak.Roles.USER)){
            storyPresentation.setApproved(false);
        }
        else{
            throw new IllegalArgumentException("Unexpected role");
        }
        storyPresentationRepository.save(storyPresentation);
        storyPresentation.getStoryPresentationFrames().forEach(x -> {
            x.setStory(storyPresentation);
            storyPresentationFramesRepository.save(x);
        });
    }


    @Transactional
    public StoryPresentationFrames addFrame(String frameDto, MultipartFile file,
                          String bankId, String platform, Long id) throws IOException {
        StoryPresentationFrames frame = mapper.readValue(
                frameDto
                , StoryPresentationFrames.class);
        return addFrameEntity(frame, file, bankId, platform, id);
    }
    @Transactional
    public StoryPresentationFrames addFrameEntity(StoryPresentationFrames frame, MultipartFile file,
                                            String bankId, String platform, Long id) throws IOException {
        frame.setId(UUID.randomUUID());

        //добавление картинки в JSON
        String presentationPictureUrl = multipartFileToImageConverter.parsePicture(
                new ImageContainer(file),
                FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/",
                id,
                frame.getId());
        frame.setPictureUrl(presentationPictureUrl);

        //Изменение JSON
        final List<StoryPresentation> listStory = getStoryList(bankId, platform);
        final StoryPresentation storyPresentation = getStoryModel(listStory, id);
        final List<StoryPresentationFrames> listFrames = storyPresentation.getStoryPresentationFrames();
        listFrames.add(frame);
        if (listFrames.size() >= MAX_COUNT_FRAME) throw new IllegalArgumentException("Cant save a frame. The maximum size is reached");
        putStoryToJson(listStory, bankId, platform);
        return frame;
    }

    /**
     * Положить истории из списка в JSON
     */
    private void putStoryToJson(List<StoryPresentation> storyPresentationList, String bankId, String platform) throws IOException {
        Map<String, List<StoryPresentation>> resultMap = new HashMap<>();
        resultMap.put(STORIES, storyPresentationList);
        File file = new File(FILES_SAVE_DIRECTORY, FileNameCreator.createJsonName(bankId, platform));
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, resultMap);
    }

    private StoryPresentationFrames getFrameFromStory(StoryPresentation storyPresentation, String id){
        final StoryPresentationFrames storyPresentationFrames = storyPresentation
                .getStoryPresentationFrames()
                .stream().filter(x -> x.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow(() ->
                    new IllegalArgumentException(String.format("Could not find frame with id = %s", id)));
        return storyPresentationFrames;
    }

    /**
     * Изменение общих параметров истории, а именно previewTitle, previewTitleColor, previewGradient
     * @param storiesRequestDto
     * @param bankId
     * @param platform
     * @param id
     * @throws IOException
     */
    public void changeStory(String storiesRequestDto, MultipartFile file, String bankId, String platform, Long id) throws IOException {
        StoryPatchDto story = mapper.readValue(
                storiesRequestDto
                , StoryPatchDto.class);
        //Берем нужную историю из списка
        List<StoryPresentation> storyPresentationList = getStoryList(bankId, platform);
        final StoryPresentation storyPresentation = getStoryModel(storyPresentationList, id);

        //Меняем картинку
        if (file != null) {
            String pictureUrl = multipartFileToImageConverter.parsePicture(
                    new ImageContainer(file),
                    FILES_SAVE_DIRECTORY + bankId + "/" + platform + "/",
                    id);
            storyPresentation.setPreviewUrl(pictureUrl);
        }

        //обновляем значение и записываем в JSON
        String json = mapper.writeValueAsString(story);
        mapper.readerForUpdating(storyPresentation).readValue(json);
        putStoryToJson(storyPresentationList, bankId, platform);
    }

    /**
     * Метод для изменения карточки в историях
     * @param storyFramesRequestDto
     * @param bankId
     * @param platform
     * @param id
     * @param frameId
     * @throws IOException
     */
    public void changeFrameStory(String storyFramesRequestDto, String bankId, String platform,
                                 Long id,
                                 String frameId,
                                 MultipartFile file) throws IOException {
        StoryFramesDto story = mapper.readValue(
                storyFramesRequestDto
                , StoryFramesDto.class);

        List<StoryPresentation> storyPresentationList = getStoryList(bankId, platform);
        StoryPresentation storyPresentation = getStoryModel(storyPresentationList, id);
        final StoryPresentationFrames storyPresentationFrames = getFrameFromStory(storyPresentation, frameId);

        //Меняем картинку
        if (file != null) {
            String pictureUrl = multipartFileToImageConverter.parsePicture(
                    new ImageContainer(file),
                    FILES_SAVE_DIRECTORY + bankId + "/" + platform + "/",
                    id,
                    UUID.fromString(frameId));
            storyPresentationFrames.setPictureUrl(pictureUrl);
        }

        //обновляем значение и записываем в JSON
        String json = mapper.writeValueAsString(story);
        mapper.readerForUpdating(storyPresentationFrames).readValue(json);
        putStoryToJson(storyPresentationList, bankId, platform);
    }

    /**
     * Метод для удаления истории
     * @param bankId Имя банка
     * @param platform Тип платформы
     * @param id Id истории
     * @return
     * @throws Throwable
     */
    public ResponseEntity<?> deleteService(String bankId, String platform, String id) throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> deleteJson = executor.submit(()->{
            try {
                deleteJsonStories(bankId, platform, id);
            }
            catch (IOException e){
                throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
            }
        });
        Future<?> deleteImages = executor.submit(() -> {
            try {
                deleteFilesStories(bankId, platform, id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            deleteJson.get();
            deleteImages.get();
        } catch (ExecutionException ex) {
            throw ex.getCause();
        }
        return new ResponseEntity<>(HttpStatus.valueOf(202));
    }
    /**
     * Метод, предназначенный для удаления историй из JSON.
     */
    private void deleteJsonStories(String bankId, String platform, String id) throws IOException {
        //Берем список историй
        List<StoryPresentation> list = getStoryList(bankId, platform);
        //удаляем нужную историю
        if (!list.removeIf(k -> id.equals(k.getId().toString()))){
            throw new IllegalArgumentException(String.format(
                    "Could not find the frame with id = %s",
                    id)
            );
        }
        //кладем в json
        putStoryToJson(list, bankId, platform);
    }

    /**
     * Метод, предназначенный для удаления файлов историй из директории.
     */
    private void deleteFilesStories(String bankId, String platform, String id) throws InterruptedException {
        File directory = dirProcess.checkDirectoryBankAndPlatformIsExist(bankId, platform);
        File[] files = directory.listFiles();
        Stream.of(files)
                .filter(x -> x.getName().startsWith(id))
                .forEach(x -> {
                    if (x.exists()) {
                        x.delete();
                    }
                    else log.error("File " + x.getName() + " is already deleted");
                });

    }


    /**
     * Метод, предназначенный для удаления одной карточки из историй.
     * deleteJsonFrame - удаляет frame из JSON
     * deleteFileFrame - удаляет файл из директории
     *
     * @param bankId   Банк
     * @param platform Платформа
     * @param id       id истории
     * @param frameId  UUID карточки
     */
    public ResponseEntity<?> deleteStoryFrame(String bankId, String platform, String id, String frameId) throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //для передачи UUID между потоками
        Exchanger<UUID> exchanger = new Exchanger<>();
        Future<?> deleteJson = executor.submit(()->{
            try {
                exchanger.exchange(deleteJsonFrame(bankId, platform, id, frameId));
            }
            catch (IOException e){
                throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IndexOutOfBoundsException e){
                throw new IllegalArgumentException(String.format("Frame with id=%s doesnt exist", frameId));
            }
        });
        Future<?> deleteImages = executor.submit(() -> {
            try {
                deleteFileFrame(bankId, platform, id, exchanger.exchange(null));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            deleteJson.get();
            deleteImages.get();
        } catch (ExecutionException ex) {
            throw ex.getCause();
        }
        return new ResponseEntity<>(HttpStatus.valueOf(202));
    }

    /**
     * Метод предназначенный для удаления карточки внутри JSON
     * @param bankId Банк
     * @param platform Платформа
     * @param id id истории
     * @param frameId UUID карточки
     * @return
     * @throws IOException
     */
    private UUID deleteJsonFrame(String bankId, String platform, String id, String frameId) throws IOException {
        UUID uuid = null;
        //Берем историю из списка историй
        List<StoryPresentation> list = getStoryList(bankId, platform);
        StoryPresentation story = getStoryModel(list, Long.parseLong(id));

        //Берем все карточки историй
        List<StoryPresentationFrames> frames = story.getStoryPresentationFrames();
        //Получаем UUID нужной истории и удаляем ее
        uuid = UUID.fromString(frameId);

        if (frames.size() == 1) throw new IllegalArgumentException("Can't delete a single frame");
        if (!frames.removeIf(x -> x.getId().equals(UUID.fromString(frameId)))){
            throw new IllegalArgumentException(String.format(
                    "Could not find the frame with id = %s",
                    id
            ));
        }
        //Записываем в JSON
        putStoryToJson(list, bankId, platform);
        return uuid;
    }
    /**
     * Метод, предназначенный для удаления файлов при удалении карточки
     * @param bankId
     * @param platform
     * @param id
     * @param frameId
     */
    private void deleteFileFrame(String bankId, String platform, String id, UUID frameId) throws InterruptedException {
        File directory = dirProcess.checkDirectoryBankAndPlatformIsExist(bankId, platform);
        File[] files = directory.listFiles();
        Stream.of(files)
                .filter(x -> x.getName().startsWith(id+"_"+frameId))
                .findFirst()
                .map(File::delete);
    }

    /**
     * Метод для свапа порядка историй
     * @param id
     * @param bankId
     * @param platform
     * @param fristUuid
     * @param secondUuid
     * @throws IOException
     */
    public void swapFrames(Long id, String bankId, String platform, String fristUuid, String secondUuid) throws IOException {
        final List<StoryPresentation> storyPresentationList = getStoryList(bankId, platform);
        final StoryPresentation storyPresentation = getStoryModel(storyPresentationList, id);
        final List<StoryPresentationFrames> frames = storyPresentation.getStoryPresentationFrames();
        int first = IntStream.range(0, frames.size())
                .filter(streamIndex -> fristUuid.equals(frames.get(streamIndex).getId().toString()))
                .findFirst()
                .orElse(-1);
        int second = IntStream.range(0, frames.size())
                .filter(streamIndex -> secondUuid.equals(frames.get(streamIndex).getId().toString()))
                .findFirst()
                .orElse(-1);

        Collections.swap(frames, first, second);
        storyPresentation.setStoryPresentationFrames(frames);
        putStoryToJson(storyPresentationList, bankId, platform);
    }

}
