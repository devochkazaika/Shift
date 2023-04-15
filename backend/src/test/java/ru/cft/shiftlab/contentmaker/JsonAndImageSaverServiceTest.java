package ru.cft.shiftlab.contentmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonAndImageSaverService;
import ru.cft.shiftlab.contentmaker.util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JsonAndImageSaverServiceTest {

    private final FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();

    private final ByteArrayToImageConverter byteArrayToImageConverter = new ByteArrayToImageConverter();

    private final FileNameCreator fileNameCreator = new FileNameCreator();

    private final DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());
    private final ImageNameGenerator imageNameGenerator = new ImageNameGenerator();

    private final JsonAndImageSaverService jsonAndImageSaverService = new JsonAndImageSaverService(
            fileNameCreator,
            fileExtensionExtractor,
            byteArrayToImageConverter,
            dtoToEntityConverter,
            imageNameGenerator);

    @Test
    void should_save_files() throws IOException {
        BufferedImage bImage = ImageIO.read(
                new File("/content-maker/backend/src/test/java/ru/cft/shiftlab/contentmaker/test_pictures",
                        "sample.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte[] bytes = bos.toByteArray();
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Заголовок",
                "Текст карточки",
                "FF0000",
                bytes,
                "NONE",
                "",
                "",
                "",
                "",
                "",
                "",
                "EMPTY");
        StoryDto storyDto = new StoryDto(
                "Заголовок превью",
                "FF0000",
                bytes,
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto)));
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto("absolutbank", new ArrayList<>(Collections.singletonList(storyDto)));

        jsonAndImageSaverService.saveFiles(storiesRequestDto, true);

        DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

        String picturesDirectory = "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/"
                + storiesRequestDto.getBankId() + "/";
        String fileName = "test.json";
        String jsonDirectory = "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";
        String previewUrl = picturesDirectory + "/preview1.png";

        List<StoryPresentation> storyPresentationList = new ArrayList<>();

        storyPresentationList.add(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                "absolutbank",
                storyDto,
                jsonDirectory,
                picturesDirectory,
                previewUrl));

        storyPresentationList.get(0).getStoryPresentationFrames().get(0).setPictureUrl("/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/absolutbank/storyFramePicture1.png");
        storyPresentationList.get(0).setPreviewUrl("/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/absolutbank/preview1.png");

        Map<String, List<StoryPresentation>> presentationList = new HashMap<>();
        presentationList.put("stories", storyPresentationList);

        Path jsonFilePath = Paths.get(jsonDirectory + fileName);
        Path previewPictureFilePath = Paths.get(picturesDirectory + "preview1.png");
        Path storyFramePictureFilePath = Paths.get(picturesDirectory + "storyFramePicture1.png");

        File jsonFile = jsonFilePath.toFile();
        File previewPicture = previewPictureFilePath.toFile();
        File storyFramePicture = storyFramePictureFilePath.toFile();

        assertAll(
                () -> assertTrue(Files.exists(jsonFile.toPath()), "File should exist"),
                () -> assertTrue(Files.exists(previewPicture.toPath()), "File should exist"),
                () -> assertTrue(Files.exists(storyFramePicture.toPath()), "File should exist"),
                () -> assertLinesMatch(Collections.singletonList(asJsonString(presentationList)),
                        Files.readAllLines(jsonFile.toPath())));
    }

     public static String asJsonString(final Object obj) {
         try {
             return new ObjectMapper().writeValueAsString(obj);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
     }
 }
