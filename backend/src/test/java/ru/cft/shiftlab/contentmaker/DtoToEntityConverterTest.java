package ru.cft.shiftlab.contentmaker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shiftlab.contentmaker.util.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DtoToEntityConverterTest {

    private ModelMapper modelMapper = new ModelMapper();
    private DtoToEntityConverter converterRequestDto = new DtoToEntityConverter(modelMapper);
    private StoryPresentation storyPresentation = new StoryPresentation();


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



        storyPresentation = converterRequestDto.fromStoryDtoToStoryPresentation("id", storyDto);



        assertAll (
                () -> assertEquals(storyDto.getPreviewTitle(), storyPresentation.getPreviewTitle()),
                () -> assertEquals(storyDto.getPreviewTitleColor(), storyPresentation.getPreviewTitleColor()),
                () -> assertTrue(Arrays.equals(storyDto.getPreviewUrl(), bytes)),
                () -> assertEquals(storyDto.getPreviewGradient(), storyPresentation.getPreviewGradient()),

                () -> assertAll (
                        () -> assertEquals(storyFramesDto.getTitle(), storyPresentation.getStoryPresentationFrames().get(0).getTitle()),
                        () -> assertEquals(storyFramesDto.getText(), storyPresentation.getStoryPresentationFrames().get(0).getText()),
                        () -> assertEquals(storyFramesDto.getTextColor(), storyPresentation.getStoryPresentationFrames().get(0).getTextColor()),
                        () -> assertTrue(Arrays.equals(storyFramesDto.getPictureUrl(), storyPresentation.getStoryPresentationFrames().get(0).getPictureUrl())),
                        () -> assertEquals(storyFramesDto.getVisibleLinkOrButtonOrNone(), storyPresentation.getStoryPresentationFrames().get(0).getVisibleLinkOrButtonOrNone()),
                        () -> assertTrue(storyFramesDto.getLinkUrl().equals(storyPresentation.getStoryPresentationFrames().get(0).getLinkUrl())),
                        () -> assertEquals(storyFramesDto.getLinkText(), storyPresentation.getStoryPresentationFrames().get(0).getLinkText()),
                        () -> assertTrue(storyFramesDto.getButtonUrl().equals(storyPresentation.getStoryPresentationFrames().get(0).getButtonUrl())),
                        () -> assertEquals(storyFramesDto.getButtonBackgroundColor(), storyPresentation.getStoryPresentationFrames().get(0).getButtonBackgroundColor()),
                        () -> assertEquals(storyFramesDto.getButtonText(), storyPresentation.getStoryPresentationFrames().get(0).getButtonText()),
                        () -> assertEquals(storyFramesDto.getButtonTextColor(), storyPresentation.getStoryPresentationFrames().get(0).getButtonTextColor()),
                        () -> assertEquals(storyFramesDto.getGradient(), storyPresentation.getStoryPresentationFrames().get(0).getGradient())
                )
        );
    }
}
