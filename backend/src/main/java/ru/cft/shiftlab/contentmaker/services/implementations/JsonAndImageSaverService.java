package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.services.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.ByteArrayToImageConverter;
import ru.cft.shiftlab.contentmaker.util.FileExtensionExtractor;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JsonAndImageSaverService implements FileSaverService {

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ByteArrayToImageConverter byteArrayToImageConverter = new ByteArrayToImageConverter();
            String jsonDirectory =
                    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story";
            String fileName = "stories.json";
            mapper.writeValue(new File(jsonDirectory, fileName), storiesRequestDto);
            int counterForPreview = 0;
            int counterForStoryFramePicture = 0;
            String picturesDirectory =
                    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/test_bank";
            FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();
            for (StoryDto story: storiesRequestDto.getStoryDtos()) {
                counterForPreview++;
                byte [] previewUrl = story.getPreviewUrl();
                String previewFileExtension =
                        fileExtensionExtractor.getFileExtensionFromByteArray(previewUrl);
                byteArrayToImageConverter.convertByteArrayToImageAndSave(
                        previewUrl,
                        picturesDirectory,
                        "preview",
                        previewFileExtension,
                        counterForPreview);
                for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                    counterForStoryFramePicture++;
                    byte [] storyFramePictureUrl = storyFramesDto.getPictureUrl();
                    String storyFramePictureFileExtension =
                            fileExtensionExtractor.getFileExtensionFromByteArray(storyFramePictureUrl);
                    byteArrayToImageConverter.convertByteArrayToImageAndSave(
                            storyFramePictureUrl,
                            picturesDirectory,
                            "storyFramePicture",
                            storyFramePictureFileExtension,
                            counterForStoryFramePicture);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
