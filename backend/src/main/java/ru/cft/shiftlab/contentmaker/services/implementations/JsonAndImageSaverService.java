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
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;
import ru.cft.shiftlab.contentmaker.util.DtoToEntityConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JsonAndImageSaverService implements FileSaverService {

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonDirectory =
                    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story";
            String fileName = FileNameCreator.createFileName(storiesRequestDto.getStoryDtos().get(0).getPreviewTitle());

            DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

            Map<String, List<StoryPresentation>> presentationList =
                    dtoToEntityConverter.fromStoriesRequestDtoToMap(storiesRequestDto);


            mapper.writeValue(new File(jsonDirectory, fileName), presentationList);
            int counterForPreview = 0;
            int counterForStoryFramePicture = 0;
            String picturesDirectory = "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/"
                            + storiesRequestDto.getStoryDtos().get(0).getPreviewTitle();
            File newDirectory = new File(picturesDirectory);
            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
