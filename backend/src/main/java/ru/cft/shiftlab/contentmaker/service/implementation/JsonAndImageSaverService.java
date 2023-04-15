package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Имплементация интерфейса FIleSaverService.
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

    private String filesSaveDirectory = "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto, boolean testOrNot){
        try {
            String bankId = storiesRequestDto.getBankId();
            String picturesSaveDirectory = filesSaveDirectory + bankId + "/";

            File newDirectory = new File(picturesSaveDirectory);

            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }

            String fileName = fileNameCreator.createFileName(bankId);

            if (testOrNot) {
                fileName = "test.json";
            }

            List<StoryPresentation> storyPresentationList = new ArrayList<>();

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
                    storyPresentationList.get(counterForPreview - 1).getStoryPresentationFrames().get(counterForStoryFramePicture - 1).setPictureUrl(storyFramePictureUrl);

                }
            }
            Map<String, List<StoryPresentation>> resultMap = new HashMap<>();

            resultMap.put("stories", storyPresentationList);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(filesSaveDirectory, fileName), resultMap);

        }
        catch (IOException e) {
            throw new StaticContentException("Could not save files", "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
    }

}
