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
import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;
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
    private final WhiteList whiteList = new WhiteList();

    private final FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();

    private final ByteArrayToImageConverter byteArrayToImageConverter = new ByteArrayToImageConverter();

    private FileNameCreator fileNameCreator = new FileNameCreator(whiteList);

    private final DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

    private JsonAndImageSaverService jsonAndImageSaverService = new JsonAndImageSaverService(fileNameCreator,
                                                                                            fileExtensionExtractor,
                                                                                            byteArrayToImageConverter,
                                                                                            dtoToEntityConverter);

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
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto("absolutbank", new ArrayList<>(Collections.singletonList(storyDto)));

        jsonAndImageSaverService.saveFiles(storiesRequestDto);

        DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

        String picturesDirectory = "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/"
                + storiesRequestDto.getBankId() + "/";
        String fileName = fileNameCreator.createFileName(storiesRequestDto.getBankId());
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
