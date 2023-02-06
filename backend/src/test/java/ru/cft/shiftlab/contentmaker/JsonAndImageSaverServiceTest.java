package ru.cft.shiftlab.contentmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JsonAndImageSaverServiceTest {

    @InjectMocks
    private JsonAndImageSaverService jsonAndImageSaverService;
    @Test
    void should_save_files() throws IOException {
        BufferedImage bImage = ImageIO.read(
                new File("/content-maker/backend/src/test/java/ru/cft/shiftlab/contentmaker/test_pictures",
                        "sample.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte[] bytes = bos.toByteArray();
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Конвертируй",
                "Обменивайте валюту онлайн по выгодному курсу",
                "FFFFFF",
                bytes,
                "NONE",
                "link url",
                "link text",
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY");
        StoryDto storyDto = new StoryDto(
                "Конвертируй",
                "FFFFFF",
                bytes,
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto)));
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto("id", new ArrayList<>(Collections.singletonList(storyDto)));

        jsonAndImageSaverService.saveFiles(storiesRequestDto);

        String picturesDirectory = "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/"
                + storiesRequestDto.getStoryDtos().get(0).getPreviewTitle();
        String fileName = FileNameCreator.createFileName(storiesRequestDto.getStoryDtos().get(0).getPreviewTitle());

        Path jsonFilePath = Paths.get("/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/" + fileName);
        Path previewPictureFilePath = Paths.get(picturesDirectory +"/preview1.png");
        Path storyFramePictureFilePath = Paths.get(picturesDirectory + "/storyFramePicture1.png");

        File jsonFile = jsonFilePath.toFile();
        File previewPicture = previewPictureFilePath.toFile();
        File storyFramePicture = storyFramePictureFilePath.toFile();

        assertAll(
                () -> assertTrue(Files.exists(jsonFile.toPath()), "File should exist"),
                () -> assertTrue(Files.exists(previewPicture.toPath()), "File should exist"),
                () -> assertTrue(Files.exists(storyFramePicture.toPath()), "File should exist"));
                //() -> assertLinesMatch(Collections.singletonList(asJsonString(storiesRequestDto)),
               //         Files.readAllLines(jsonFile.toPath())));
    }

     public static String asJsonString(final Object obj) {
         try {
             return new ObjectMapper().writeValueAsString(obj);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
     }
 }
