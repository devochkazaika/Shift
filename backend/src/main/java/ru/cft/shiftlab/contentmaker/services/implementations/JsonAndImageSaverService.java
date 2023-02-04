package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.services.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.ByteArrayToImageConverter;
import ru.cft.shiftlab.contentmaker.util.FileExtensionExtractor;
import ru.cft.shiftlab.contentmaker.util.RequestDtoToEntityConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonAndImageSaverService implements FileSaverService {

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonDirectory =
                    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story";
            String fileName = "stories.json";
            List<StoryPresentation> presentationList = new ArrayList<>();
            RequestDtoToEntityConverter requestDtoToEntityConverter = new RequestDtoToEntityConverter(new ModelMapper());
            for (StoryDto storyDto: storiesRequestDto.getStoryDtos()) {
                presentationList.add(requestDtoToEntityConverter.fromStoriesRequestDtoToStoryPresentation(storyDto));
            }
            mapper.writeValue(new File(jsonDirectory, fileName), presentationList);
            int counterForPreview = 0;
            int counterForStoryFramePicture = 0;
            String picturesDirectory =
                    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/test_bank";
            for (StoryDto story: storiesRequestDto.getStoryDtos()) {
                counterForPreview++;
                byte [] previewUrl = story.getPreviewUrl();
                String previewFileExtension =
                        FileExtensionExtractor.getFileExtensionFromByteArray(previewUrl);
                ByteArrayToImageConverter.convertByteArrayToImageAndSave(
                        previewUrl,
                        picturesDirectory,
                        "preview",
                        previewFileExtension,
                        counterForPreview);
                for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                    counterForStoryFramePicture++;
                    byte [] storyFramePictureUrl = storyFramesDto.getPictureUrl();
                    String storyFramePictureFileExtension =
                            FileExtensionExtractor.getFileExtensionFromByteArray(storyFramePictureUrl);
                    ByteArrayToImageConverter.convertByteArrayToImageAndSave(
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
