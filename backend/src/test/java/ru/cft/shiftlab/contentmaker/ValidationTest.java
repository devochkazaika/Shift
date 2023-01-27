package ru.cft.shiftlab.contentmaker;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.validation.implementations.StoryFramesValidator;
import ru.cft.shiftlab.contentmaker.validation.implementations.StoryValidator;
import ru.cft.shiftlab.contentmaker.validation.StoryFramesValid;
import ru.cft.shiftlab.contentmaker.validation.StoryValid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@ExtendWith(MockitoExtension.class)
public class ValidationTest {
    @Mock
    StoryValid storyValid;

    @Mock
    StoryFramesValid storyFramesValid;

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

        StoryValidator storyValidator = new StoryValidator();
        storyValidator.initialize(storyValid);

        StoryFramesValidator storyFramesValidator = new StoryFramesValidator();
        storyFramesValidator.initialize(storyFramesValid);



        assertTrue(
                storyValidator.isValid(storyDto, constraintValidatorContext) &&
                storyFramesValidator.isValid(storyFramesDto, constraintValidatorContext));
    }

    @Test
    public void validate_DtoHaveIncorrectDataForStoryValid() {
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
                "Конвертируй\nвалюту\nонлайн", //titleMaxStringCount: 2
                "FFFFFF",
                bytes,
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto))
        );
        StoryValidator storyValidator = new StoryValidator();
        storyValidator.initialize(storyValid);



        assertFalse(storyValidator.isValid(storyDto, constraintValidatorContext));
    }

    @Test
    public void validate_DtoHaveIncorrectDataForStoryFramesValid() {
        byte[] bytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Онлайн конвертация ", //titleMaxLenForOneString: 17
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
        StoryFramesValidator storyFramesValidator = new StoryFramesValidator();
        storyFramesValidator.initialize(storyFramesValid);



        assertFalse(storyFramesValidator.isValid(storyFramesDto, constraintValidatorContext));
    }
}