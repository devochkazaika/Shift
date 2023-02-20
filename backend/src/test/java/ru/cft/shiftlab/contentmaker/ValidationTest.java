package ru.cft.shiftlab.contentmaker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.util.WhiteList;
import ru.cft.shiftlab.contentmaker.validation.WhitelistValid;
import ru.cft.shiftlab.contentmaker.validation.implementations.BankIdValidator;
import ru.cft.shiftlab.contentmaker.validation.implementations.StoryFramesValidator;
import ru.cft.shiftlab.contentmaker.validation.implementations.StoryValidator;
import ru.cft.shiftlab.contentmaker.validation.StoryFramesValid;
import ru.cft.shiftlab.contentmaker.validation.StoryValid;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@ExtendWith(MockitoExtension.class)
public class ValidationTest {
    @Mock
    StoryValid storyValid;

    @Mock
    StoryFramesValid storyFramesValid;

    @Mock
    WhitelistValid whitelistValid;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private final WhiteList whiteList = new WhiteList();

    @Test
    public void validate_DtosHaveAllCorrectData() throws IOException {
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

        StoryValidator storyValidator = new StoryValidator();
        storyValidator.initialize(storyValid);

        StoryFramesValidator storyFramesValidator = new StoryFramesValidator();
        storyFramesValidator.initialize(storyFramesValid);



        assertTrue(
                storyValidator.isValid(storyDto, constraintValidatorContext) &&
                storyFramesValidator.isValid(storyFramesDto, constraintValidatorContext));
    }

    @Test
    public void validate_DtoHaveIncorrectDataForStoryValid() throws IOException {
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
    public void validate_DtoHaveIncorrectDataForStoryFramesValid() throws IOException {
        BufferedImage bImage = ImageIO.read(
                new File("/content-maker/backend/src/test/java/ru/cft/shiftlab/contentmaker/test_pictures",
                        "sample.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte[] bytes = bos.toByteArray();
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Онлайн конвертация ", 
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


    @Test
    public void validate_BankIdCorrect() {

        String bankId = "nskbl";

        BankIdValidator bankIdValidator = new BankIdValidator(whiteList);
        bankIdValidator.initialize(whitelistValid);

        assertTrue(bankIdValidator.isValid(bankId, constraintValidatorContext));
    }

    @Test
    public void validate_BankIdIncorrect() {

        String bankId = "noneBankIdIncorrect";

        BankIdValidator bankIdValidator = new BankIdValidator(whiteList);
        bankIdValidator.initialize(whitelistValid);

        assertFalse(bankIdValidator.isValid(bankId, constraintValidatorContext));
    }
}