package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;
import ru.cft.shiftlab.contentmaker.service.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
import ru.cft.shiftlab.contentmaker.util.MultipartBodyProcess;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.STORIES;


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
        mapper.configure(DeserializationFeature
                        .FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;
    private final DirProcess dirProcess;

    public HttpEntity<MultiValueMap<String, HttpEntity<?>>> getFilePlatform(String bankId, String platform) {
        String filePlatform = FileNameCreator.createJsonName(bankId, platform);
        Map<String, List<StoryPresentation>> resultMap;
        try {
            resultMap = mapper.readValue(new File(FILES_SAVE_DIRECTORY, filePlatform), new TypeReference<>(){});
        } catch (IOException e) {
            throw new StaticContentException("Некорректный файл json на стороне сервера", "404");
        }

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        //Добавление json из истории в multipartBodyBuilder
        String jsonAsString;
        try {
            jsonAsString = mapper.writeValueAsString(resultMap.get("stories"));
        } catch (JsonProcessingException e) {
            throw new StaticContentException("Некорректный файл json на стороне сервера", "404");
        }
        MultipartBodyProcess.addJsonInBuilderMultipart(jsonAsString, multipartBodyBuilder);

        //Добавление картинок из истории в multipartBodyBuilder
        resultMap.get("stories").forEach(
                storyPresentation->{
                    MultipartBodyProcess.addImageInBuilderMultipart(storyPresentation.getPreviewUrl(), multipartBodyBuilder);
                    storyPresentation.getStoryPresentationFrames().forEach(
                            frame-> MultipartBodyProcess.addImageInBuilderMultipart(frame.getPictureUrl(), multipartBodyBuilder)
                    );
                }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return new HttpEntity<>(multipartBodyBuilder.build(), headers);
    }


    @Override
    public void saveFiles(String strStoriesRequestDto, MultipartFile previewImage, MultipartFile[] images){
        try {
            StoriesRequestDto storiesRequestDto = mapper.readValue(
                    mapper.readValue(strStoriesRequestDto, String.class)
                    , StoriesRequestDto.class);

            String bankId = storiesRequestDto.getBankId();
            String platformType = storiesRequestDto.getPlatformType();

            String picturesSaveDirectory = FILES_SAVE_DIRECTORY + bankId + "/" + platformType + "/";
            //Создание пути для картинок, если его еще нет
            dirProcess.createFolders(picturesSaveDirectory);
            //Чтение сторис, которые уже находятся в хранилище
            List<StoryPresentation> storyPresentationList = dirProcess.checkFileInBankDir(
                    FileNameCreator.createJsonName(bankId, platformType),
                    STORIES
            );
            storiesDtoToPresentations(
                    bankId,
                    picturesSaveDirectory,
                    storiesRequestDto,
                    storyPresentationList,
                    previewImage,
                    images
            );

            Map<String, List<StoryPresentation>> resultMap = new HashMap<>();
            resultMap.put(STORIES, storyPresentationList);
            File file = new File(FILES_SAVE_DIRECTORY, FileNameCreator.createJsonName(bankId, platformType));
            mapper.writeValue(file, resultMap);
        }
        catch (JsonProcessingException e){
            throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        catch (IOException e) {
            throw new StaticContentException("Could not save files", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
    }


    private void storiesDtoToPresentations(
            String bankId,
            String picturesSaveDirectory,
            StoriesRequestDto storiesRequestDto,
            List<StoryPresentation> storyPresentationList,
            MultipartFile previewImage,
            MultipartFile[] images
    ) throws IOException {
        ImageContainer imageContainer = new ImageContainer(images);
        for (StoryDto story: storiesRequestDto.getStoryDtos()) {
            String previewUrl;
            if(previewImage == null){
                previewImage = images[0];
            }
            ImageContainer imageContainerPreview = new ImageContainer(previewImage);
            long lastId;
            if (storyPresentationList.isEmpty()){
                lastId = 0;
            }
            else {
                var lastOfList = storyPresentationList.get(storyPresentationList.size()-1);
                lastId = (lastOfList.getId()==null) ? 0 : lastOfList.getId()+1;
            }

            //Добавление к старым картинкам _old
            FileNameCreator.renameOld(picturesSaveDirectory, lastId);

            previewUrl = multipartFileToImageConverter.parsePicture(
                    imageContainerPreview,
                    picturesSaveDirectory,
                    lastId);
            var storyPresentation = dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                    bankId,
                    story,
                    previewUrl);
            storyPresentation.setId(lastId);

            ArrayList<StoryPresentationFrames> storyPresentationFramesList = new ArrayList<>();
            for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                var storyPresentationFrame = dtoToEntityConverter.fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto);
                String presentationPictureUrl = multipartFileToImageConverter.parsePicture(
                        imageContainer,
                        picturesSaveDirectory,
                        storyPresentation.getId(),
                        storyPresentationFrame.getId());
                storyPresentationFrame.setPictureUrl(presentationPictureUrl);
                storyPresentationFramesList.add(storyPresentationFrame);
            }
            storyPresentation.setStoryPresentationFrames(storyPresentationFramesList);

            storyPresentationList.add(storyPresentation);
        }
    }

    public ResponseEntity<?> deleteService(String bankId, String platform, String id) throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable r = ()->{
            try {
                deleteJsonStories(bankId, platform, id);
            }
            catch (IOException e){
                throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
            }
        };
        Future<?> deleteJson = executor.submit(r);
        Future<?> deleteImages = executor.submit(() -> deleteFilesStories(bankId, platform, id));
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
        deleteFromJson(bankId, platform, id);
    }
    private void deleteFromJson(String bankId, String platform, String id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String fileName = FileNameCreator.createJsonName(bankId, platform);
        ObjectNode node = (ObjectNode) mapper.readTree(new File(FILES_SAVE_DIRECTORY + "/" + fileName));
        if (node.has("stories")) {
            ArrayNode storiesNode = (ArrayNode) node.get("stories");
            Iterator<JsonNode> i = storiesNode.iterator();
            String IdBank;
            String IdFrame;
            while (i.hasNext()){
                JsonNode k = i.next();
                if (id.indexOf("_") > 0) {
                    IdBank = id.substring(0, id.indexOf("_"));
                    IdFrame = id.substring(id.indexOf("_") + 1);
                    //именно такая проверка, а не по индексу, так как элемент может удалиться и id будут 3, 8, 10, например
                    if (IdBank.equals(k.get("id").toString())) {
                        ArrayNode listFrames = (ArrayNode) k.get("storyFrames");
                        listFrames.remove(Integer.parseInt(IdFrame));
                    }
                }
                else{
                    if (id.equals(k.get("id").toString())) {
                       i.remove();
                    }
                }
            }
        }
        else{
            throw new StaticContentException("Field stories not created",
                    "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        JsonNode js = (JsonNode) node;
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILES_SAVE_DIRECTORY, fileName), js);
    }
    /**
     * Метод, предназначенный для удаления файлов историй из директории.
     */
    private void deleteFilesStories(String bankId, String platform, String id){
        File directory = new File(FILES_SAVE_DIRECTORY + "/" + bankId + "/" + platform);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new StaticContentException("Directory does not exist or is not a directory: " + directory.getAbsolutePath(),
                    "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
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
     * @return
     */
    public ResponseEntity deleteStoryFrame(String bankId, String platform, String id, String frameId) throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Exchanger<UUID> exchanger = new Exchanger<>();
        Runnable r = ()->{
            try {
                exchanger.exchange(deleteJsonFrame(bankId, platform, id, frameId));
            }
            catch (IOException e){
                throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Future<?> deleteJson = executor.submit(r);
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

    private void deleteFileFrame(String bankId, String platform, String id, UUID frameId){
        File directory = new File(FILES_SAVE_DIRECTORY + "/" + bankId + "/" + platform);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new StaticContentException("Directory does not exist or is not a directory: " + directory.getAbsolutePath(),
                    "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        File[] files = directory.listFiles();
        Stream.of(files)
                .filter(x -> x.getName().startsWith(id))
                .forEach(x -> {
                    if (x.getName().startsWith(id+"_"+frameId)) {
                        x.delete();
                    }
                }
                );
    }

    private UUID deleteJsonFrame(String bankId, String platform, String id, String frameId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String fileName = FileNameCreator.createJsonName(bankId, platform);
        UUID uuid = null;
        ObjectNode node = (ObjectNode) mapper.readTree(new File(FILES_SAVE_DIRECTORY + "/" + fileName));
        if (node.has("stories")) {
            ArrayNode storiesNode = (ArrayNode) node.get("stories");
            Iterator<JsonNode> i = storiesNode.iterator();
            while (i.hasNext()){
                JsonNode k = i.next();
                if (id.indexOf("_") > 0) {
                    //именно такая проверка, а не по индексу, так как элемент может удалиться и id будут 3, 8, 10, например
                    if (bankId.equals(k.get("id").toString())) {
                        ArrayNode listFrames = (ArrayNode) k.get("storyFrames");
                        String json = mapper.writeValueAsString(listFrames.get(Integer.parseInt(frameId)).get("id"));
                        uuid = mapper.readValue(json, UUID.class);
                        listFrames.remove(Integer.parseInt(frameId));
                    }
                }
                else{
                    if (id.equals(k.get("id").toString())) {
                        i.remove();
                    }
                }
            }
        }
        else{
            throw new StaticContentException("Field stories not created",
                    "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        JsonNode js = (JsonNode) node;
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILES_SAVE_DIRECTORY, fileName), js);
        return uuid;
    }

}
