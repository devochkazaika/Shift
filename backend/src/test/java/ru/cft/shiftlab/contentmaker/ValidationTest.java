package ru.cft.shiftlab.contentmaker;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shiftlab.contentmaker.dto.StoriesDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.validation.Impl.StoriesTitleTextValidator;
import ru.cft.shiftlab.contentmaker.validation.Impl.StoryTitleValidator;
import ru.cft.shiftlab.contentmaker.validation.StoriesMultipleTitleTextValid;
import ru.cft.shiftlab.contentmaker.validation.StoryTitleValid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;

import static org.junit.Assert.assertTrue;


@ExtendWith(MockitoExtension.class)
public class ValidationTest {
    @Mock
    StoryTitleValid storyTitleValid;

    @Mock
    StoriesMultipleTitleTextValid storiesMultipleTitleTextValid;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void validate_DtosHaveAllCorrectData() {
        byte[] bytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");

        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Конвертируй",
                "Обменивайте валюту онлайн по выгодному курсу",
                "FFFFFF",
                bytes,
                "text",
                "link url",
                true,
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY"
        );

        StoriesDto storiesDto = new StoriesDto(
                "Конвертируй валюту",
                "FFFFFF",
                bytes,
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto))
        );

        StoryTitleValidator storyTitleValidator = new StoryTitleValidator();
        storyTitleValidator.initialize(storyTitleValid);

        StoriesTitleTextValidator storiesTitleTextValidator = new StoriesTitleTextValidator();
        storiesTitleTextValidator.initialize(storiesMultipleTitleTextValid);



        assertTrue(
                storyTitleValidator.isValid(storiesDto, constraintValidatorContext) &&
                storiesTitleTextValidator.isValid(storyFramesDto, constraintValidatorContext));
    }
}