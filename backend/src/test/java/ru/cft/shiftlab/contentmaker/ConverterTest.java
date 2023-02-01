package ru.cft.shiftlab.contentmaker;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import ru.cft.shiftlab.contentmaker.converter.ConverterRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {

    private ModelMapper modelMapper = new ModelMapper();

    private ConverterRequestDto converterRequestDto = new ConverterRequestDto(modelMapper);

    StoryPresentation storyPresentation = new StoryPresentation();

    @Test
    public void whenConvertStoryRequestDtoToStoryPresentation_thenCorrect() {
        byte[] bytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");

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

        //StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);

        storyPresentation = converterRequestDto.fromStoriesRequestDtoToStoryPresentation(storyDto);

        assertAll (
                () -> assertEquals(storyDto.getPreviewTitle(), storyPresentation.getPreviewTitle()),
                () -> assertEquals(storyDto.getPreviewTitleColor(), storyPresentation.getPreviewTitleColor())
                //...
        );
    }
}
