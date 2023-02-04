// package ru.cft.shiftlab.contentmaker;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.junit.jupiter.MockitoExtension;
// import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
// import ru.cft.shiftlab.contentmaker.dto.StoryDto;
// import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
// import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;

<<<<<<< HEAD
// import java.io.File;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.HexFormat;
=======
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
>>>>>>> abb33d2b531cdc2eddc837834264cab396228708

// import static org.junit.jupiter.api.Assertions.*;

// @ExtendWith(MockitoExtension.class)
// public class JsonAndImageSaverServiceTest {

//     @InjectMocks
//     private JsonAndImageSaverService jsonAndImageSaverService;

<<<<<<< HEAD
//     @Test
//     void should_save_files() {
//         byte[] bytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
//         StoryFramesDto storyFramesDto = new StoryFramesDto(
//                 "Конвертируй",
//                 "Обменивайте валюту онлайн по выгодному курсу",
//                 "FFFFFF",
//                 bytes,
//                 "NONE",
//                 "link url",
//                 "link text",
//                 "Попробовать",
//                 "FFFFFF",
//                 "FFFFFF",
//                 "buttonurl",
//                 "EMPTY");
//         StoryDto storyDto = new StoryDto(
//                 "Конвертируй валюту",
//                 "FFFFFF",
//                 bytes,
//                 "EMPTY",
//                 new ArrayList<>(Collections.singletonList(storyFramesDto)));
//         StoriesRequestDto storiesRequestDto = new StoriesRequestDto(new ArrayList<>(Collections.singletonList(storyDto)));
=======
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
                "Конвертируй валюту",
                "FFFFFF",
                bytes,
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto)));
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto(new ArrayList<>(Collections.singletonList(storyDto)));
>>>>>>> abb33d2b531cdc2eddc837834264cab396228708

//         jsonAndImageSaverService.saveFiles(storiesRequestDto);

<<<<<<< HEAD
//         Path path = Paths.get("/content-maker/backend/src/main/resources/data/stories.json");
//         File file = path.toFile();

//         assertAll(
//                 () -> assertTrue(Files.exists(file.toPath()), "File should exist"),
//                 () -> assertLinesMatch(Collections.singletonList(asJsonString(storiesRequestDto)),
//                         Files.readAllLines(file.toPath())));
//     }
=======
        Path jsonFilePath = Paths.get("/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/stories.json");
        Path previewPictureFilePath = Paths.get("/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/test_bank/preview1.png");
        Path storyFramePictureFilePath = Paths.get("/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/test_bank/storyFramePicture1.png");

        File jsonFile = jsonFilePath.toFile();
        File previewPicture = previewPictureFilePath.toFile();
        File storyFramePicture = storyFramePictureFilePath.toFile();

        assertAll(
                () -> assertTrue(Files.exists(jsonFile.toPath()), "File should exist"),
                () -> assertTrue(Files.exists(previewPicture.toPath()), "File should exist"),
                () -> assertTrue(Files.exists(storyFramePicture.toPath()), "File should exist"),
                () -> assertLinesMatch(Collections.singletonList(asJsonString(storiesRequestDto)),
                        Files.readAllLines(jsonFile.toPath())));
    }
>>>>>>> abb33d2b531cdc2eddc837834264cab396228708

//     public static String asJsonString(final Object obj) {
//         try {
//             return new ObjectMapper().writeValueAsString(obj);
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//     }
// }
