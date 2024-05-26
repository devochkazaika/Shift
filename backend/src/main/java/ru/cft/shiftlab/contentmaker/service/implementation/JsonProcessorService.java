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
import org.springframework.core.io.ByteArrayResource;
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
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;
import ru.cft.shiftlab.contentmaker.service.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public HttpEntity<MultiValueMap<String, HttpEntity<?>>> getFilePlatform(String bankId, String platform) {
        String filePlatform = FileNameCreator.createFileName(bankId, platform);
        Map<String, List<StoryPresentation>> resultMap;
        try {
            resultMap = mapper.readValue(new File(FILES_SAVE_DIRECTORY, filePlatform), new TypeReference<>(){});
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        try {
            multipartBodyBuilder.part("json", mapper.writeValueAsString(resultMap.get("stories")));
        } catch (JsonProcessingException e) {
            throw new StaticContentException("Некорректный файл json на стороне сервера", "404");
        }

        resultMap.get("stories").forEach(
                storyPresentation->{
                    addInBuilderMultipart(storyPresentation.getPreviewUrl(), multipartBodyBuilder);
                    storyPresentation.getStoryPresentationFrames().forEach(
                            frame-> addInBuilderMultipart(frame.getPictureUrl(), multipartBodyBuilder)
                    );
                }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return new HttpEntity<>(multipartBodyBuilder.build(), headers);
    }

    void addInBuilderMultipart(String picture, MultipartBodyBuilder multipartBodyBuilder){
        File file = new File(picture);
        try {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            multipartBodyBuilder.part(file.getName(), new ByteArrayResource(fileBytes));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new StaticContentException("Сервер не может найти нужное изображение", "404");
        }
    }

    @Override
    public void saveFiles(String strStoriesRequestDto, MultipartFile previewImage, MultipartFile[] images){
        try {
            StoriesRequestDto storiesRequestDto = mapper.readValue(
                    mapper.readValue(strStoriesRequestDto, String.class)
                    , StoriesRequestDto.class);
            String bankId = storiesRequestDto.getBankId();
            String platformType = storiesRequestDto.getPlatformType();

            String picturesSaveDirectory = FILES_SAVE_DIRECTORY + bankId + "/";
            //Создание пути для картинок, если его еще нет
            FileNameCreator.createFolders(picturesSaveDirectory);
            //Чтение сторис, которые уже находятся в хранилище
            List<StoryPresentation> storyPresentationList = checkFileInBankDir(FileNameCreator.createFileName(bankId, platformType));
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
            File file = new File(FILES_SAVE_DIRECTORY, FileNameCreator.createFileName(bankId, platformType));
            mapper.writeValue(file, resultMap);
        }
        catch (IOException e) {
            throw new StaticContentException("Could not save files", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
    }
    private List<StoryPresentation> checkFileInBankDir(String fileName) throws IOException {
        List<StoryPresentation> storyPresentationList = new ArrayList<StoryPresentation>();
        File bankJsonFile = new File(FILES_SAVE_DIRECTORY + fileName);

        if (bankJsonFile.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, List<StoryPresentation>>> typeReference = new TypeReference<>() {
            };

            storyPresentationList.addAll(mapper.readValue(
                            new File(FILES_SAVE_DIRECTORY,fileName), typeReference)
                    .get(STORIES)
            );
        }
        return storyPresentationList;
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
                lastId = (storyPresentationList.get(storyPresentationList.size() - 1).getId()==null) ? 0 : storyPresentationList.get(storyPresentationList.size() - 1).getId()+1;
            }

            File file = new File(picturesSaveDirectory);
            File[] files = file.listFiles();
            Stream.of(files)
                    .filter(x ->x.getName().startsWith(String.valueOf(lastId)))
                    .forEach(x -> {
                        File newFile = new File(x.getParent(), x.getName().substring(0, x.getName().indexOf('.')) + "_old"+x.getName().substring(x.getName().indexOf('.')));
                        boolean b = x.renameTo(newFile);
                        if (b){
                            System.out.println("success");
                        }
                        else{
                            newFile.delete();
                            x.renameTo(newFile);
                        }
                        System.out.println(x.getAbsolutePath());
                    });

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

    public void deleteService(String bankId, String platform, String id) throws Exception {
        try {
            deleteJsonStories(bankId, platform, id);
        }
        catch (IOException e){
            throw new StaticContentException("Could not read json file", "HTTP 500 - INTERNAL_SERVER_ERROR")
        }
        deleteFilesStories(bankId, platform, id);
    }
    public void deleteJsonStories(String bankId, String platform, String id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String fileName = FileNameCreator.createFileName(bankId, platform);
        ObjectNode node = (ObjectNode) mapper.readTree(new File(FILES_SAVE_DIRECTORY + fileName));
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
    private void deleteFilesStories(String bankId, String platform, String id){
        File directory = new File(FILES_SAVE_DIRECTORY + "/" + bankId);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist or is not a directory: " + directory.getAbsolutePath());
            return;
        }
        File[] files = directory.listFiles();
        System.out.println(directory.getAbsolutePath());
        Stream.of(files)
                .filter(x -> x.getName().startsWith(id))
                .forEach(x -> x.delete());

    }
}
