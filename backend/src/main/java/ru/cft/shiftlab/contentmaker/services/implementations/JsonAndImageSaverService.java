package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JsonAndImageSaverService implements FileSaverService {


    private final FileNameCreator fileNameCreator;
    private final FileExtensionExtractor fileExtensionExtractor;
    private final ByteArrayToImageConverter byteArrayToImageConverter;

    @Autowired
    public JsonAndImageSaverService(FileNameCreator fileNameCreator,
                                    FileExtensionExtractor fileExtensionExtractor,
                                    ByteArrayToImageConverter byteArrayToImageConverter) {
        this.fileNameCreator = fileNameCreator;
        this.fileExtensionExtractor = fileExtensionExtractor;
        this.byteArrayToImageConverter = byteArrayToImageConverter;
    }

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto){
        try {
            String jsonDirectory =
                    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";

            String picturesDirectory =
                    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/"
                    + storiesRequestDto.getBankId();

            File newDirectory = new File(picturesDirectory);

            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }

            String fileName = fileNameCreator.createFileName(storiesRequestDto.getBankId());

            DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

            Map<String, List<StoryPresentation>> presentationList =
                    dtoToEntityConverter.fromStoriesRequestDtoToMap(storiesRequestDto, jsonDirectory, picturesDirectory);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(jsonDirectory, fileName), presentationList);

            int counterForPreview = 0;
            int counterForStoryFramePicture = 0;

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
