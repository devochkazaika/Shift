package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
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
import java.util.*;

/**
 * Сервис предназначенный для сохранения JSON файла и картинок.
 */
@Service
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "files.save.directory")
public class JsonProcessorService implements FileSaverService {
    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature
                        .FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }
    private final FileNameCreator fileNameCreator;
    private final FileExtensionExtractor fileExtensionExtractor;
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;

    private String filesSaveDirectory =
            "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";
    private String STORIES = "stories";

    public Map<String, List<StoryPresentation>> getFilePlatform(String bankId, String platform){
        String filePlatform = fileNameCreator.createFileName(bankId, platform);
        Map<String, List<StoryPresentation>> resultMap = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            resultMap = mapper.readValue(new File(filesSaveDirectory, filePlatform), new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return resultMap;
    }

    @Override
    public void saveFiles(String strStoriesRequestDto, MultipartFile previewImage, MultipartFile[] images, boolean testOrNot){
        try {
            StoriesRequestDto storiesRequestDto = mapper.readValue(
                    mapper.readValue(strStoriesRequestDto, String.class)
                    , StoriesRequestDto.class);
            String bankId = storiesRequestDto.getBankId();
            String picturesSaveDirectory = filesSaveDirectory + bankId + "/";

            File newDirectory = new File(picturesSaveDirectory);
            if (!newDirectory.exists()) {
                if(!newDirectory.mkdirs()){
                    throw new IOException("Can't create dir: " + picturesSaveDirectory);
                }
            }

            String platformType = storiesRequestDto.getPlatformType();

            writeImagesAndJson(fileNameCreator.createFileName(bankId, platformType),
                    previewImage,
                    images,
                    storiesRequestDto,
                    testOrNot,
                    bankId,
                    picturesSaveDirectory);
        }
        catch (IOException e) {
            throw new StaticContentException("Could not save files", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
    }

    private void writeImagesAndJson(String fileName,
                                    MultipartFile previewImage,
                                    MultipartFile[] images,
                                    StoriesRequestDto storiesRequestDto,
                                    boolean testOrNot,
                                    String bankId,
                                    String picturesSaveDirectory) throws IOException {
        List<StoryPresentation> storyPresentationList = new ArrayList<>();

        checkFileInBankDir(filesSaveDirectory, fileName, storyPresentationList);

        storiesDtoToPresentations(
                bankId,
                picturesSaveDirectory,
                storiesRequestDto,
                storyPresentationList,
                previewImage,
                images,
                testOrNot
        );

        Map<String, List<StoryPresentation>> resultMap = new HashMap<>();

        resultMap.put(STORIES, storyPresentationList);


//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filesSaveDirectory, fileName), resultMap);
        mapper.writeValue(new File(filesSaveDirectory, "fff"), storiesRequestDto);
    }

    private void checkFileInBankDir(String filesSaveDirectory, String fileName, List<StoryPresentation> storyPresentationList) throws IOException {
        File bankJsonFile = new File(filesSaveDirectory + fileName);

        if (bankJsonFile.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, List<StoryPresentation>>> typeReference = new TypeReference<>() {
            };

            storyPresentationList.addAll(mapper.readValue(
                    new File(filesSaveDirectory,fileName), typeReference)
                    .get(STORIES)
            );
        }
    }

    private void storiesDtoToPresentations(
            String bankId,
            String picturesSaveDirectory,
            StoriesRequestDto storiesRequestDto,
            List<StoryPresentation> storyPresentationList,
            MultipartFile previewImage,
            MultipartFile[] images,
            boolean testOrNot
    ) throws IOException {
        ImageContainer imageContainer = new ImageContainer(images);
        for (StoryDto story: storiesRequestDto.getStoryDtos()) {
            String previewUrl;
            if(previewImage == null){
                previewImage = images[0];
            }
            ImageContainer imageContainerPreview = new ImageContainer(previewImage);
            previewUrl = multipartFileToImageConverter.parsePicture(
                    imageContainerPreview,
                    picturesSaveDirectory);


            var storyPresentation = dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                    bankId,
                    story,
                    previewUrl);

            ArrayList<StoryPresentationFrames> storyPresentationFramesList = new ArrayList<>();
            for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                var storyPresentationFrame = dtoToEntityConverter.fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto);
                String presentationPictureUrl = multipartFileToImageConverter.parsePicture(
                        imageContainer,
                        picturesSaveDirectory);
                storyPresentationFrame.setPictureUrl(presentationPictureUrl);
                storyPresentationFramesList.add(storyPresentationFrame);
            }
            storyPresentation.setStoryPresentationFrames(storyPresentationFramesList);

            storyPresentationList.add(storyPresentation);
        }
    }
}
