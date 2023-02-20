package ru.cft.shiftlab.contentmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shiftlab.contentmaker.util.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DtoToEntityConverterTest {

    private ModelMapper modelMapper = new ModelMapper();
    private DtoToEntityConverter converterRequestDto = new DtoToEntityConverter(modelMapper);
    private StoryPresentation storyPresentation = new StoryPresentation();


    @Test
    public void whenConvertStoryRequestDtoToStoryPresentation_thenCorrect() throws IOException {
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
                "link text",
                "link url",
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY"
        );

        StoryDto storyDto = new StoryDto(
                "Конвертируй валюту",
                "FFFFFF",
                bytes,
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto))
        );



        storyPresentation = converterRequestDto.fromStoryDtoToStoryPresentation("id", storyDto, null, null);


        assertAll(
                () -> assertEquals(storyDto.getPreviewTitle(), storyPresentation.getPreviewTitle()),
                () -> assertEquals(storyDto.getPreviewTitleColor(), storyPresentation.getPreviewTitleColor()),
                () -> assertTrue(Arrays.equals(storyDto.getPreviewUrl(), bytes)),
                () -> assertEquals(storyDto.getPreviewGradient(), storyPresentation.getPreviewGradient()),

                () -> assertLinesMatch(Collections.singletonList(asJsonString(storyPresentation.getStoryPresentationFrames().get(0))),
                        Collections.singletonList(asJsonString(storyFramesDto)))
        );
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
