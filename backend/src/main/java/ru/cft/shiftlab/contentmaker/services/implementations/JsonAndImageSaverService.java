package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Map;

/**
 * Имплементация интерфейса FIleSaverService.
 */
@Service
@RequiredArgsConstructor
public class JsonAndImageSaverService implements FileSaverService {


    private final FileNameCreator fileNameCreator;
    private final FileExtensionExtractor fileExtensionExtractor;
    private final ByteArrayToImageConverter byteArrayToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;

    private final String JSON_DIRECTORY =
            "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";

    private final String PICTURES_DIRECTORY =
            "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto){
        try {

            File newDirectory = new File(PICTURES_DIRECTORY + storiesRequestDto.getBankId());

            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }

            String fileName = fileNameCreator.createFileName(storiesRequestDto.getBankId());


            Map<String, List<StoryPresentation>> presentationList =
                    dtoToEntityConverter.fromStoriesRequestDtoToMap(storiesRequestDto,
                            JSON_DIRECTORY,
                            PICTURES_DIRECTORY + storiesRequestDto.getBankId());

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(JSON_DIRECTORY, fileName), presentationList);

            int counterForPreview = 0;
            int counterForStoryFramePicture = 0;

            for (StoryDto story: storiesRequestDto.getStoryDtos()) {
                counterForPreview++;
                byte [] previewUrl = story.getPreviewUrl();
                String previewFileExtension =
                        fileExtensionExtractor.getFileExtensionFromByteArray(previewUrl);
                byteArrayToImageConverter.convertByteArrayToImageAndSave(
                        previewUrl,
                        PICTURES_DIRECTORY + storiesRequestDto.getBankId(),
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
                            PICTURES_DIRECTORY + storiesRequestDto.getBankId(),
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
