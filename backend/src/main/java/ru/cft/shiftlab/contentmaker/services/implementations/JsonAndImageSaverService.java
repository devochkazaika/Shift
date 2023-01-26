package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesDto;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.services.FileSaverService;
import ru.cft.shiftlab.util.ByteArrayToImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
            for (StoriesDto story: storiesRequestDto.getStoriesDtos()) {
                counterForPreview++;
                byte [] previewUrl = story.getPreviewUrl();
                BufferedImage bImage = ImageIO.read(new File(directory, "Cft_logo_ru.png"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos );
                byte [] data = bos.toByteArray();
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                BufferedImage bImage2 = ImageIO.read(bis);
                ImageIO.write(bImage2,
                        "png", new File(directory, "preview" + counterForPreview + ".png") );
                for (StoryFramesDto storyFramesDto: story.getStoryFramesDtos()) {
                    counterForStoryFramePicture++;
                    byte [] storyFramePictureUrl = storyFramesDto.getPictureUrl();
                    ImageIO.write(bImage2,
                            "png", new File(directory, "storyFramePicture" + counterForStoryFramePicture + ".png"));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
