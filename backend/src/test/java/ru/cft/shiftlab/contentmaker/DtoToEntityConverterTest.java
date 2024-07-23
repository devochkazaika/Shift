package ru.cft.shiftlab.contentmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shiftlab.contentmaker.util.Image.ImageNameGenerator;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DtoToEntityConverterTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final DtoToEntityConverter converterRequestDto = new DtoToEntityConverter(modelMapper, new MultipartFileToImageConverter(new ImageNameGenerator()));
    private StoryPresentation storyPresentation = new StoryPresentation();


    @Test
    public void whenConvertStoryRequestDtoToStoryPresentation_thenCorrect(){
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Конвертируй",
                "Обменивайте валюту онлайн по выгодному курсу",
                "FFFFFF",
                "NONE",
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY"
        );

        StoryDto storyDto = new StoryDto(
                "Конвертируй валюту",
                "FFFFFF",
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto))
        );

        storyPresentation = converterRequestDto.fromStoryDtoToStoryPresentation("id", storyDto, null);

        Assertions.assertAll(
                () -> assertEquals(storyDto.getPreviewTitle(), storyPresentation.getPreviewTitle()),
                () -> assertEquals(storyDto.getPreviewTitleColor(), storyPresentation.getPreviewTitleColor()),
                () -> assertEquals(storyDto.getPreviewGradient(), storyPresentation.getPreviewGradient()),

                () -> Assertions.assertEquals(storyPresentation.getPreviewTitle(), storyDto.getPreviewTitle()),
                () -> Assertions.assertEquals(storyPresentation.getPreviewGradient(), storyDto.getPreviewGradient()),
                () -> Assertions.assertEquals(storyPresentation.getPreviewTitleColor(), storyDto.getPreviewTitleColor()),
                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getText(),
                        storyDto.getStoryFramesDtos().get(0).getText()),
                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getGradient(),
                        storyDto.getStoryFramesDtos().get(0).getGradient()),
                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getButtonUrl(),
                        storyDto.getStoryFramesDtos().get(0).getButtonUrl()),
                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getTitle(),
                        storyDto.getStoryFramesDtos().get(0).getTitle()
                )
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
