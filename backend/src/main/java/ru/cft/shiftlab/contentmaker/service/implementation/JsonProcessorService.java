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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.dto.StoryPatchDto;
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
        String filePlatform = FileNameCreator.createFileName(bankId, platform);
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
                    FileNameCreator.createFileName(bankId, platformType),
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

            putStoryToJson(storyPresentationList, bankId, platformType);
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
                        storyPresentation.getId());
                storyPresentationFrame.setPictureUrl(presentationPictureUrl);
                storyPresentationFramesList.add(storyPresentationFrame);
            }
            storyPresentation.setStoryPresentationFrames(storyPresentationFramesList);

            storyPresentationList.add(storyPresentation);
        }
    }

    private List<StoryPresentation> getStoryList(String bankId, String platform) throws IOException {
        return dirProcess.checkFileInBankDir(
                FileNameCreator.createFileName(bankId, platform),
                STORIES
        );
    }
    private StoryPresentation getStoryModel(List<StoryPresentation> storyPresentationList,
                                            Long id) throws IOException {
        return storyPresentationList.stream()
                .filter(x-> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find the story with id=" + id));
    }
    private void putStoryToJson(List<StoryPresentation> storyPresentationList, String bankId, String platform) throws IOException {
        Map<String, List<StoryPresentation>> resultMap = new HashMap<>();
        resultMap.put(STORIES, storyPresentationList);
        File file = new File(FILES_SAVE_DIRECTORY, FileNameCreator.createFileName(bankId, platform));
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, resultMap);
    }

    public void changeStory(String storiesRequestDto, String bankId, String platform, Long id) throws IOException {
        StoryPatchDto story = mapper.readValue(
                storiesRequestDto
                , StoryPatchDto.class);

        List<StoryPresentation> storyPresentationList = getStoryList(bankId, platform);
        StoryPresentation storyPresentation = getStoryModel(storyPresentationList, id);

        String json = mapper.writeValueAsString(story);
        mapper.readerForUpdating(storyPresentation).readValue(json);

        putStoryToJson(storyPresentationList, bankId, platform);
    }
    public void changeFrameStory(String storyFramesRequestDt, String bankId, String platform,
                                 Long id,
                                 Integer frameId) throws IOException {
        StoryFramesDto story = mapper.readValue(
                storyFramesRequestDt
                , StoryFramesDto.class);

        List<StoryPresentation> storyPresentationList = getStoryList(bankId, platform);
        StoryPresentation storyPresentation = getStoryModel(storyPresentationList, id);
        StoryPresentationFrames storyPresentationFrames = storyPresentation.getStoryPresentationFrames().get(frameId);

        String json = mapper.writeValueAsString(story);
        mapper.readerForUpdating(storyPresentationFrames).readValue(json);

        putStoryToJson(storyPresentationList, bankId, platform);
    }

    public void deleteService(String bankId, String platform, String id) throws Exception {
        Runnable r = ()->{
            try {
                deleteJsonStories(bankId, platform, id);
            }
            catch (IOException e){
                throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR");
            }
        };
        Thread deleteJson = new Thread(r, "deleteJson");
        Thread deleteImages = new Thread(() -> deleteFilesStories(bankId, platform, id), "deleteImages");
        deleteJson.start();
        deleteImages.start();
        deleteJson.join();
        deleteImages.join();
    }
    /**
     * Метод, предназначенный для удаления историй из JSON.
     */
    public void deleteJsonStories(String bankId, String platform, String id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String fileName = FileNameCreator.createFileName(bankId, platform);
        ObjectNode node = (ObjectNode) mapper.readTree(new File(FILES_SAVE_DIRECTORY + "/" + fileName));
        if (node.has("stories")) {
            ArrayNode storiesNode = (ArrayNode) node.get("stories");
            Iterator<JsonNode> i = storiesNode.iterator();
            while (i.hasNext()){
                JsonNode k = i.next();
                if (id.equals(k.get("id").toString())) {
                    i.remove();
                }
            }
        }
        else{
            throw new IOException();
        }
        JsonNode js = (JsonNode) node;
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILES_SAVE_DIRECTORY, fileName), js);
        deleteFilesStories(bankId, platform, id);
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
                .forEach(x -> x.delete());

    }
}
