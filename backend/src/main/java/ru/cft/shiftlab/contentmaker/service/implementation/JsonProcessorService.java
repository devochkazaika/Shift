package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.aop.History;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.dto.StoryPatchDto;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationFramesRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationRepository;
import ru.cft.shiftlab.contentmaker.service.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.util.StoryMapper;
import ru.cft.shiftlab.contentmaker.util.keycloak.KeyCloak;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.MAX_COUNT_FRAME;


/**
 * Сервис предназначенный для сохранения JSON файла и картинок.
 */
@Service
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "files.save.directory")
@Log4j2
//@AllArgsConstructor
public class JsonProcessorService implements FileSaverService {
    private final StoryMapper mapper;
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;
    private final DirProcess dirProcess;
    private final StoryPresentationRepository storyPresentationRepository;
    private final StoryPresentationFramesRepository storyPresentationFramesRepository;
    private final KeyCloak keyCloak;
    private final StoryMapper storyMapper;

    /**
     * Возврат всех историй банка + платформы
     * @param bankId Имя банка
     * @param platform Тип платформы
     * @return
     * @throws IOException
     */
    public HttpEntity<List<StoryPresentation>> getFilePlatformJson(String bankId, String platform) throws IOException {
        return new HttpEntity<List<StoryPresentation>>(mapper.getStoryList(bankId, platform));
    }


    /**
     * Сохранение истории
     * @param strStoriesRequestDto DTO с информацией о Stories.
     * @param previewImage Картинка для preview
     * @param imagesFrame Массив картинок для карточек
     * @return
     */
    @Override
    @History(operationType = "create")
    public StoryPresentation saveFiles(String strStoriesRequestDto,
                          MultipartFile previewImage,
                          MultipartFile[] imagesFrame){
        try {
            LinkedList<MultipartFile> images = new LinkedList<>(Arrays.asList(imagesFrame));
            images.addFirst(previewImage);
            StoriesRequestDto storiesRequestDto = mapper.readValue(
                    mapper.readValue(strStoriesRequestDto, String.class)
                    , StoriesRequestDto.class);
            //Сохранение в БД
            var storyEntity = saveStoryEntity(storiesRequestDto, images);
            //Сохранение
            return saveByRoles(storyEntity);
        }
        catch (JsonProcessingException e){
            throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        catch (IOException e) {
            throw new StaticContentException("Could not save files", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
    }

    private StoryPresentation saveStoryEntity(StoriesRequestDto storiesRequestDto,
                                                  LinkedList<MultipartFile> images) throws IOException {
        //Создание пути для картинок, если его еще нет
        String picturesSaveDirectory = FILES_SAVE_DIRECTORY+storiesRequestDto.getBankId()+"/"+storiesRequestDto.getPlatform()+"/";
        dirProcess.createFolders(picturesSaveDirectory);
        StoryPresentation storyPresentation = dtoToEntityConverter.fromStoryRequestDtoToStoryPresentation(storiesRequestDto);
        final StoryPresentation story = storyPresentationRepository.save(storyPresentation);
        final List<StoryPresentationFrames> storyPresentationFrames = storyPresentation.getStoryPresentationFrames();
        String previewUrl = multipartFileToImageConverter.parsePicture(
                new ImageContainer(images.removeFirst()),
                picturesSaveDirectory,
                story.getId());
        storyPresentation.setPreviewUrl(previewUrl);
        for (int i=0; i<storyPresentationFrames.size(); i++){
            final StoryPresentationFrames frame = storyPresentationFrames.get(i);
            frame.setStory(story);
            frame.setId(storyPresentationFramesRepository.save(frame).getId());
            frame.setPictureUrl(multipartFileToImageConverter.parsePicture(
                    new ImageContainer(images.removeFirst()),
                    picturesSaveDirectory,
                    story.getId(),
                    frame.getId()));
            storyPresentationFrames.set(i, storyPresentationFramesRepository.save(frame));
        }
        return storyPresentationRepository.save(storyPresentation);
    }


    /**
     * Сохранение истории в зависимости от роли
     * @param storyPresentation
     * @throws IOException
     */
    private StoryPresentation saveByRoles(StoryPresentation storyPresentation) throws IOException {
        Set<KeyCloak.Roles> roles = keyCloak.getRoles();
        String bankId = storyPresentation.getBankId();
        String platformType = storyPresentation.getPlatform();
        List<StoryPresentation> storyPresentationList = mapper.getStoryList(bankId, platformType);
        storyPresentationList.add(storyPresentation);

        if (roles.contains(KeyCloak.Roles.ADMIN)){
            mapper.putStoryToJson(storyPresentationList, bankId, platformType);
            storyPresentation.setApproved(StoryPresentation.Status.APPROVED);
        }
        else if (roles.contains(KeyCloak.Roles.USER)){
            storyPresentation.setApproved(StoryPresentation.Status.NOTAPPROVED);
        }
        else{
            throw new IllegalArgumentException("Unexpected role");
        }
        storyPresentation.getStoryPresentationFrames().forEach(x -> {
            x.setStory(storyPresentation);
            storyPresentationFramesRepository.save(x);
        });
        return storyPresentationRepository.save(storyPresentation);
    }

    /**
     * Если истории не существует в БД, но существует в JSON
     */
    private void saveStoryToDbIfNotExist(final StoryPresentation st,
                                     Long id){
        //Если не сохранена сама история
        var story = storyPresentationRepository.findById(id).orElse(
                storyPresentationRepository.save(st)
        );
        st.setId(story.getId());
        //Если карточки не сохранены
        storyPresentationFramesRepository.saveAll(st.getStoryPresentationFrames());
    }

    /**
     * Сохранение карточки в зависимости от роли
     * @param frame
     * @param file
     * @param id
     * @param bankId
     * @param platformType
     * @throws IOException
     */
    private StoryPresentation saveToJsonByRoles(StoryPresentationFrames frame, MultipartFile file, Long id, String bankId, String platformType) throws IOException {
        List<StoryPresentation> storyPresentationList = mapper.getStoryList(bankId, platformType);

        // нахождение истории в JSON
        final StoryPresentation story = mapper.getStoryModel(storyPresentationList, id);
        saveStoryToDbIfNotExist(story, id);

        // Сохранение карточки
        if (story.getStoryPresentationFrames().size() >= MAX_COUNT_FRAME) throw new IllegalArgumentException("Could not save frame because max count is achieved");
        frame.setStory(story);
        frame = storyPresentationFramesRepository.save(frame);

        // Добавление картинки в JSON
        String presentationPictureUrl = multipartFileToImageConverter.parsePicture(
                new ImageContainer(file),
                FILES_SAVE_DIRECTORY+bankId+"/"+platformType+"/",
                id,
                frame.getId());
        frame.setPictureUrl(presentationPictureUrl);
        story.getStoryPresentationFrames().add(frame);

        // Сохранение в зависимости от роли (ПОКА НЕ ДОПИСАНО)
//        Set<KeyCloak.Roles> roles = keyCloak.getRoles();
//        if (roles.contains(KeyCloak.Roles.ADMIN)){
//            mapper.putStoryToJson(storyPresentationList, bankId, platformType);
//            story.setApproved(StoryPresentation.Status.APPROVED);
//        }
//        else if (roles.contains(KeyCloak.Roles.USER)){
//            mapper.putStoryToJson(storyPresentationList, bankId, platformType);
//            story.setApproved(StoryPresentation.Status.NOTAPPROVED);
//        }
        mapper.putStoryToJson(storyPresentationList, bankId, platformType);
        return story;
    }

    @Override
    public List<StoryPresentation> getUnApprovedStories() {
        return storyPresentationRepository.getUnApprovedStories();
    }

    @Override
    public List<StoryPresentation> getDeletedStories() {
        return storyPresentationRepository.getDeletedStories();
    }

    @Override
    public List<StoryPresentation> getUnApprovedStories(String bankId, String platform) {
        return storyPresentationRepository.getUnApprovedStories(bankId, platform);
    }

    @Override
    public List<StoryPresentation> getDeletedStories(String bankId, String platform) {
        var story = storyPresentationRepository.getDeletedStories(bankId, platform);

        return storyPresentationRepository.getDeletedStories(bankId, platform);
    }

    /**
     * Подтверждение истории админом и последующее сохранение в JSON
     * @param bankId Имя банка
     * @param platform Платформа
     * @param id Id истории
     * @throws IOException
     */
    @Override
    @History(operationType = "update")
    public StoryPresentation approveStory(String bankId, String platform, Long id) throws IOException {
        final var story = storyPresentationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find the story with id = %d", id)));
        story.setApproved(StoryPresentation.Status.APPROVED);
        storyPresentationRepository.save(story);
        mapper.putStoryToJson(story, bankId, platform);
        return story;
    }

    @Transactional
    public StoryPresentationFrames addFrame(String frameDto, MultipartFile file,
                                            String bankId, String platform, Long id) throws IOException {
        StoryPresentationFrames frame = mapper.readValue(
                frameDto
                , StoryPresentationFrames.class);
        //Сохранение в БД и в JSON
        saveToJsonByRoles(frame, file, id, bankId, platform);
        return frame;
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

    public StoryPresentation changeStoryByUser(StoryPresentation story, List<StoryPresentation> storyPresentationList,
                                                String json, MultipartFile file) throws IOException {
        // Меняем картинку
        if (file != null) {
            String pictureUrl = multipartFileToImageConverter.parsePicture(
                    new ImageContainer(file),
                    FILES_SAVE_DIRECTORY + story.getBankId() + "/" + story.getBankId() + "/",
                    story.getId());
            story.setPreviewUrl(pictureUrl);
        }
        mapper.readerForUpdating(story).readValue(json);
        mapper.putStoryToJson(storyPresentationList, story.getBankId(), story.getPlatform());
        story = storyPresentationRepository.save(story);
        return story;
    }

    private StoryPresentation changeStoryByAdmin(StoryPresentation story, List<StoryPresentation> storyPresentationList,
                                                 String json, MultipartFile file) throws IOException {
        var changedStory = story.withId(null);
        mapper.readerForUpdating(changedStory).readValue(json);
        changedStory.setApproved(StoryPresentation.Status.CHANGED);
        changedStory.setStoryPresentationFrames(null);
        storyPresentationRepository.save(changedStory);
        // Создаем картинку картинку
        if (file != null) {
            String pictureUrl = multipartFileToImageConverter.parsePicture(
                    new ImageContainer(file),
                    FILES_SAVE_DIRECTORY + story.getBankId() + "/" + story.getPlatform() + "/",
                    story.getId(),
                    changedStory.getId());
            changedStory.setPreviewUrl(pictureUrl);
        }
        return storyPresentationRepository.save(changedStory);
    }

    /**
     * Изменение общих параметров истории, а именно previewTitle, previewTitleColor, previewGradient
     * @param storiesRequestDto
     * @param bankId
     * @param platform
     * @param id
     * @throws IOException
     */
    @History(operationType = "Change")
    @Transactional
    public StoryPresentation changeStory(String storiesRequestDto, MultipartFile file, String bankId, String platform, Long id) throws IOException {
        StoryPatchDto storyDto = mapper.readValue(
                storiesRequestDto
                , StoryPatchDto.class);
        // Берем нужную историю из списка
        List<StoryPresentation> storyPresentationList = mapper.getStoryList(bankId, platform);
        StoryPresentation storyEntity = mapper.getStoryModel(storyPresentationList, id);

        // Если карточки не сохранены в бд
        saveStoryToDbIfNotExist(storyEntity, id);

        // Обновляем значение и записываем в JSON
        String json = mapper.writeValueAsString(storyDto);
        Set<KeyCloak.Roles> roles = keyCloak.getRoles();
        if (roles.contains(KeyCloak.Roles.ADMIN)) {
            return changeStoryByUser(storyEntity, storyPresentationList, json, file);
        }
        else if (roles.contains(KeyCloak.Roles.USER)) {
            return changeStoryByAdmin(storyEntity, storyPresentationList, json, file);
        }

        return storyEntity;
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

        List<StoryPresentation> storyPresentationList = mapper.getStoryList(bankId, platform);
        final StoryPresentation storyEntity = mapper.getStoryModel(storyPresentationList, id);
        final StoryPresentationFrames storyPresentationFrames = getFrameFromStory(storyEntity, frameId);
        // Если история и карточки не сохранены в БД
        saveStoryToDbIfNotExist(storyEntity, id);

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
        storyPresentationFrames.setStory(storyEntity);
        storyPresentationFramesRepository.save(storyPresentationFrames);
        mapper.putStoryToJson(storyPresentationList, bankId, platform);
    }

    /**
     * Метод для удаления истории
     * @param bankId Имя банка
     * @param platform Тип платформы
     * @param id Id истории
     * @return
     * @throws Throwable
     */
    @History(operationType = "delete")
    public ResponseEntity<?> deleteService(String bankId, String platform, Long id) throws Throwable {
        // Если истории нет в БД, но есть в JSON, тогда сохраняется в БД и удаляется из JSON
        final var storyPresentation = storyPresentationRepository.findById(id).orElseGet(() -> {
            StoryPresentation story = null;
            try {
                story = mapper.getStoryModel(mapper.getStoryList(bankId, platform), id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return storyPresentationRepository.save(story);
        });
        deleteJsonStories(bankId, platform, id);
        storyPresentation.setApproved(StoryPresentation.Status.DELETED);
        storyPresentationRepository.save(storyPresentation);

        return new ResponseEntity<>(HttpStatus.valueOf(202));
    }

    @Transactional
    @Modifying
    public ResponseEntity<?> deleteStoriesFromDb(String bankId, String platform, Long id) throws Throwable{
        storyPresentationFramesRepository.deleteByStoryId(id);
        storyPresentationRepository.deleteById(id);
        deleteFilesStories(bankId, platform, id);
        return new ResponseEntity<>(HttpStatus.valueOf(202));
    }
    /**
     * Метод, предназначенный для удаления историй из JSON.
     */
    private StoryPresentation deleteJsonStories(String bankId, String platform, Long id) throws IOException {
        //Берем список историй
        List<StoryPresentation> list = mapper.getStoryList(bankId, platform);

        //удаляем нужную историю
        StoryPresentation storyDeleted = null;
        for (int i = 0; i < list.size(); i++) {
            storyDeleted = list.get(i);
            if (storyDeleted.getId().equals(id)) {
                list.remove(i);
                break;
            }
        }
        if (storyDeleted == null) throw new IllegalArgumentException(String.format(
                "Could not find the frame with id = %s",
                id)
        );

        //кладем в json
        mapper.putStoryToJson(list, bankId, platform);
        return storyDeleted;
    }

    /**
     * Метод, предназначенный для удаления файлов историй из директории.
     */
    private void deleteFilesStories(String bankId, String platform, Long id) throws InterruptedException {
        File directory = dirProcess.checkDirectoryBankAndPlatformIsExist(bankId, platform);
        File[] files = directory.listFiles();
        Stream.of(files)
                .filter(x -> x.getName().startsWith(id.toString()))
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
    @Modifying
    public ResponseEntity<?> deleteStoryFrame(String bankId, String platform, String id, String frameId) throws Throwable {
        UUID uuid = deleteJsonFrame(bankId, platform, id, frameId);
        deleteFileFrame(bankId, platform, id, uuid);
        deleteFrameFromDb(uuid);
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
        List<StoryPresentation> list = mapper.getStoryList(bankId, platform);
        StoryPresentation story = mapper.getStoryModel(list, Long.parseLong(id));

        //Берем все карточки историй
        List<StoryPresentationFrames> frames = story.getStoryPresentationFrames();
        //Получаем UUID нужной истории и удаляем ее
        uuid = UUID.fromString(frameId);

        if (frames.size() == 1) throw new IllegalArgumentException("Can't delete a single frame");
        if (!frames.removeIf(x -> x.getId().equals(UUID.fromString(frameId)))){
            throw new IllegalArgumentException(String.format(
                    "Could not find the frame with id = %s",
                    frameId
            ));
        }
        //Записываем в JSON
        mapper.putStoryToJson(list, bankId, platform);
        return uuid;
    }

    @Transactional
    public void deleteFrameFromDb(UUID uuid) {
        var story = storyPresentationFramesRepository.findById(uuid).orElse(null);
        if (story != null) {
            storyPresentationFramesRepository.deleteById(uuid);
        }
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
     * @throws IOException
     */
    public void swapFrames(Long id, String bankId, String platform, List<String> newOrder) throws IOException {
        final List<StoryPresentation> storyPresentationList = mapper.getStoryList(bankId, platform);
        final StoryPresentation storyPresentation = mapper.getStoryModel(storyPresentationList, id);
        final List<StoryPresentationFrames> frames = storyPresentation.getStoryPresentationFrames();
        frames.stream().forEach(x -> x.setStory(storyPresentation));

        for (int i=0; i<newOrder.size()-1; i++){
            for (int j=0; j<newOrder.size(); j++){
                if (frames.get(j).getId().equals(UUID.fromString(newOrder.get(i)))){
                    StoryPresentationFrames temp = frames.get(i);
                    frames.set(i, frames.get(j));
                    frames.set(j, temp);
                    break;
                }
            }
        }

        storyPresentation.setStoryPresentationFrames(frames);
        mapper.putStoryToJson(storyPresentationList, bankId, platform);
        storyPresentationRepository.save(storyPresentation);
    }

    /**
     * Метод для восстановления истории из бд
     * @param id Id истории
     * @throws IOException
     */
    @Override
    public void restoreStory(Long id) throws IOException {
        final var storyPresentation = storyPresentationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find story with id = %d", id)));
        if (storyPresentation.getApproved().equals(StoryPresentation.Status.APPROVED)) throw new IllegalArgumentException("Story is already restored");

        storyPresentation.setApproved(StoryPresentation.Status.APPROVED);
        storyPresentationRepository.save(storyPresentation);
        mapper.putStoryToJson(storyPresentation, storyPresentation.getBankId(), storyPresentation.getPlatform());
    }

    @Override
    public List<StoryPresentation> getChangedRequest(String bank, String platform) {
        var li = storyPresentationRepository.getChangeRequest(bank, platform);
        return li;
    }

    @Modifying
    @Transactional
    public StoryPresentation updatePreview(Long changing, Long changeable){
        var mainStory = storyPresentationRepository.findById(changeable).orElseThrow(
                () -> new IllegalArgumentException("Could not find story with id = " + changeable)
        );
        var changingStory = storyPresentationRepository.findById(changing).orElseThrow(
                () -> new IllegalArgumentException("Could not find story with id = " + changing)
        );
        var file = new File(changingStory.getPreviewUrl());
        new File(mainStory.getPreviewUrl()).delete();
        file.renameTo(new File(mainStory.getPreviewUrl()));
        mainStory = storyMapper.updateStoryEntity(mainStory, changingStory);
        mainStory.setApproved(StoryPresentation.Status.APPROVED);
        return storyPresentationRepository.save(mainStory);
    }

    @Modifying
    public void rollBackChangeRequest(Long idOperation){

    }
}