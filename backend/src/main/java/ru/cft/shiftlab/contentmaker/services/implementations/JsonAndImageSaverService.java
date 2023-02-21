package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;

import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.services.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.ByteArrayToImageConverter;
import ru.cft.shiftlab.contentmaker.util.FileExtensionExtractor;
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;
import ru.cft.shiftlab.contentmaker.util.DtoToEntityConverter;

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
@ConfigurationProperties(prefix = "files.save.directory")
public class JsonAndImageSaverService implements FileSaverService {

    private final FileNameCreator fileNameCreator;
    private final FileExtensionExtractor fileExtensionExtractor;
    private final ByteArrayToImageConverter byteArrayToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto){
        try {
            String filesSaveDirectory = "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";
            String bankId = storiesRequestDto.getBankId();
            String picturesSaveDirectory = filesSaveDirectory + bankId + "/";

            File newDirectory = new File(picturesSaveDirectory);

            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }

            String fileName = fileNameCreator.createFileName(bankId);

            List<StoryPresentation> storyPresentationList = new ArrayList<>();

            int counterForPreview = 0;
            int counterForStoryFramePicture = 0;

            for (StoryDto story: storiesRequestDto.getStoryDtos()) {
                counterForPreview++;
                byte [] previewUrlBytes = story.getPreviewUrl();

                String previewFileExtension =
                        fileExtensionExtractor.getFileExtensionFromByteArray(previewUrlBytes);
                byteArrayToImageConverter.convertByteArrayToImageAndSave(
                        previewUrlBytes,
                        picturesSaveDirectory,
                        "preview",
                        previewFileExtension,
                        counterForPreview);

                String previewUrl = picturesSaveDirectory + "preview" + counterForPreview + "." + previewFileExtension;
                storyPresentationList.add(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                        bankId,
                        story,
                        filesSaveDirectory,
                        picturesSaveDirectory,
                        previewUrl));

                for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                    counterForStoryFramePicture++;
                    byte [] storyFramePictureUrlBytes = storyFramesDto.getPictureUrl();

                    String storyFramePictureFileExtension =
                            fileExtensionExtractor.getFileExtensionFromByteArray(storyFramePictureUrlBytes);
                    byteArrayToImageConverter.convertByteArrayToImageAndSave(
                            storyFramePictureUrlBytes,
                            picturesSaveDirectory,
                            "storyFramePicture",
                            storyFramePictureFileExtension,
                            counterForStoryFramePicture);

                    String storyFramePictureUrl = picturesSaveDirectory + "storyFramePicture" + counterForStoryFramePicture + "." + storyFramePictureFileExtension;
                    storyPresentationList.get(counterForPreview - 1).getStoryPresentationFrames().get(counterForStoryFramePicture - 1).setPictureUrl(storyFramePictureUrl);

                }
            }
            Map<String, List<StoryPresentation>> resultMap = new HashMap<>();

            resultMap.put("stories", storyPresentationList);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(filesSaveDirectory, fileName), resultMap);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
