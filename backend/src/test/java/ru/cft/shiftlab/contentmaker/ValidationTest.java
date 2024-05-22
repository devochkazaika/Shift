package ru.cft.shiftlab.contentmaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.util.WhiteList;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.WhiteListValid;
import ru.cft.shiftlab.contentmaker.util.validation.validator.BankIdValidator;
import ru.cft.shiftlab.contentmaker.util.validation.validator.StoryFramesValidator;
import ru.cft.shiftlab.contentmaker.util.validation.validator.StoryValidator;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.StoryFramesValid;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.StoryValid;

import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class ValidationTest {
    @Mock
    StoryValid storyValid;

    @Mock
    StoryFramesValid storyFramesValid;

    @Mock
    WhiteListValid whitelistValid;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private final WhiteList whiteList = new WhiteList();

    @Test
    public void validate_DtosHaveAllCorrectData() {
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Конвертируй",
                "Обменивайте валюту онлайн\n по выгодному курсу",
                "FFFFFF",
                "NONE",
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY"
        );

        StoryDto storyDto = new StoryDto(
                "Конвертируй\nвалюту",
                "FFFFFF",
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto))
        );

        StoryValidator storyValidator = new StoryValidator();
        storyValidator.initialize(storyValid);

        StoryFramesValidator storyFramesValidator = new StoryFramesValidator();
        storyFramesValidator.initialize(storyFramesValid);

        Assertions.assertTrue(storyValidator.isValid(storyDto, constraintValidatorContext) &&
                storyFramesValidator.isValid(storyFramesDto, constraintValidatorContext));
    }

    @Test
    public void validate_DtoHaveIncorrectDataForStoryValid(){
        StoryDto storyDto = getStoryDto("Обменивайте валюту онлайн по выгодному курсу",
                "Конвертируй\nвалюту\nонлайн");
        StoryValidator storyValidator = new StoryValidator();
        storyValidator.initialize(storyValid);

        Assertions.assertFalse(storyValidator.isValid(storyDto, constraintValidatorContext));
    }

    private static StoryDto getStoryDto(String textCard, String previewTitle) {
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Конвертируй",
                textCard,
                "FFFFFF",
                "NONE",
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY"
        );
        StoryDto storyDto = new StoryDto(
                previewTitle, //titleMaxStringCount: 2
                "FFFFFF",
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto))
        );
        return storyDto;
    }

    @Test
    public void validate_DtoHaveIncorrectDataForStoryFramesValid(){
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Онлайн конвертация ", 
                "Обменивайте валюту онлайн по выгодному курсу",
                "FFFFFF",
                "NONE",
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY"
        );
        StoryFramesValidator storyFramesValidator = new StoryFramesValidator();
        storyFramesValidator.initialize(storyFramesValid);

        Assertions.assertFalse(storyFramesValidator.isValid(storyFramesDto, constraintValidatorContext));
    }

    @Test
    public void validate_BankIdCorrect() {
        String bankId = "nskbl";

        BankIdValidator bankIdValidator = new BankIdValidator(whiteList);
        bankIdValidator.initialize(whitelistValid);

        Assertions.assertTrue(bankIdValidator.isValid(bankId, constraintValidatorContext));
    }

    @Test
    public void validate_BankIdIncorrect() {
        String bankId = "noneBankIdIncorrect";

        BankIdValidator bankIdValidator = new BankIdValidator(whiteList);
        bankIdValidator.initialize(whitelistValid);

        Assertions.assertFalse(bankIdValidator.isValid(bankId, constraintValidatorContext));
    }
}