package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;

import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
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
public class JsonAndImageSaverService implements FileSaverService {

    private final FileNameCreator fileNameCreator;
    private final FileExtensionExtractor fileExtensionExtractor;
    private final ByteArrayToImageConverter byteArrayToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;
    private final ImageNameGenerator imageNameGenerator;

    private String filesSaveDirectory =
            "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";
    private String STORIES = "stories";

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto, boolean testOrNot){
        try {
            String bankId = storiesRequestDto.getBankId();
            String picturesSaveDirectory = filesSaveDirectory + bankId + "/";

            File newDirectory = new File(picturesSaveDirectory);

            if (!newDirectory.exists()) {
                if(newDirectory.mkdirs()){
                    throw new IOException("Can't create dir: " + picturesSaveDirectory);
                }
            }

            String platformType = storiesRequestDto.getPlatformType();
            if(platformType.equals("ALL PLATFORMS")){
                writeIntoFileJson(fileNameCreator.createFileName(bankId, "IOS"),
                        storiesRequestDto,
                        testOrNot,
                        bankId,
                        picturesSaveDirectory);
                writeIntoFileJson(fileNameCreator.createFileName(bankId, "ANDROID"),
                        storiesRequestDto,
                        testOrNot,
                        bankId,
                        picturesSaveDirectory);
            }else if(platformType.equals("ANDROID") || platformType.equals("IOS")){
                writeIntoFileJson(fileNameCreator.createFileName(bankId, platformType),
                        storiesRequestDto,
                        testOrNot,
                        bankId,
                        picturesSaveDirectory);
            }
        }
        catch (IOException e) {
            throw new StaticContentException("Could not save files", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
    }

    private void writeIntoFileJson(String fileName,
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
                testOrNot
        );

        Map<String, List<StoryPresentation>> resultMap = new HashMap<>();

        resultMap.put(STORIES, storyPresentationList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filesSaveDirectory, fileName), resultMap);
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
            boolean testOrNot
    ) throws IOException {
        int counterForPreview = 0;
        int counterForStoryFramePicture = 0;

        for (StoryDto story: storiesRequestDto.getStoryDtos()) {
            counterForPreview++;

            if(story.getPreviewUrl() == null) {
                story.setPreviewUrl(
                        story
                                .getStoryFramesDtos()
                                .get(0)
                                .getPictureUrl());
            }

            byte [] previewUrlBytes = story.getPreviewUrl();

            String previewPictureName = imageNameGenerator.generateImageName();

            if (testOrNot) {
                previewPictureName = "preview1";
            }

            String previewFileExtension =
                    fileExtensionExtractor.getFileExtensionFromByteArray(previewUrlBytes);
            byteArrayToImageConverter.convertByteArrayToImageAndSave(
                    previewUrlBytes,
                    picturesSaveDirectory,
                    previewPictureName,
                    previewFileExtension);

            String previewUrl = picturesSaveDirectory + previewPictureName + "." + previewFileExtension;
            storyPresentationList.add(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                    bankId,
                    story,
                    filesSaveDirectory,
                    picturesSaveDirectory,
                    previewUrl));

            for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                counterForStoryFramePicture++;
                byte [] storyFramePictureUrlBytes = storyFramesDto.getPictureUrl();

                String storyFramePictureName = imageNameGenerator.generateImageName();

                if (testOrNot) {
                    storyFramePictureName = "storyFramePicture1";
                }

                String storyFramePictureFileExtension =
                        fileExtensionExtractor.getFileExtensionFromByteArray(storyFramePictureUrlBytes);
                byteArrayToImageConverter.convertByteArrayToImageAndSave(
                        storyFramePictureUrlBytes,
                        picturesSaveDirectory,
                        storyFramePictureName,
                        storyFramePictureFileExtension);

                String storyFramePictureUrl = picturesSaveDirectory + storyFramePictureName + "." + storyFramePictureFileExtension;
                storyPresentationList.get(counterForPreview - 1).
                        getStoryPresentationFrames().
                        get(counterForStoryFramePicture - 1).
                        setPictureUrl(storyFramePictureUrl);

            }
        }
    }

}
