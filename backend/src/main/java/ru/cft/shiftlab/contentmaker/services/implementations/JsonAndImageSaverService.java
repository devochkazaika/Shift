package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.services.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.ByteArrayToImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
            String directory = "/content-maker/backend/src/main/resources/data";
            String fileName = "stories.json";
            mapper.writeValue(new File(directory, fileName), storiesRequestDto);
            int counterForPreview = 0;
            int counterForStoryFramePicture = 0;
            for (StoryDto story: storiesRequestDto.getStoryDtos()) {
                counterForPreview++;
                BufferedImage bImage = ImageIO.read(new File(directory, "Cft_logo_ru.png"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos );
                byte [] data = bos.toByteArray();
                byteArrayToImageConverter.convertByteArrayToImageAndSave(data, directory,
                        "preview", ".png", counterForPreview);
                for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                    counterForStoryFramePicture++;
                    byteArrayToImageConverter.convertByteArrayToImageAndSave(data, directory,
                            "storyFramePicture", ".png", counterForStoryFramePicture);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
