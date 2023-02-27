package ru.cft.shiftlab.contentmaker.validation.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.validation.StoryFramesValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Имлпементация валидации StoryFramesDto.
 */
@Slf4j
@EnableConfigurationProperties(StoryFramesValidator.class)
@ConfigurationProperties(prefix = "title.text.validator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryFramesValidator implements ConstraintValidator<StoryFramesValid, StoryFramesDto> {
    private int titleMaxLenForOneString = 17;
    private int textMaxLenForOneString = 245;
    private int textMaxStringCountForOneString = 7;


    private int titleMaxLenForTwoString = 34;
    private int textMaxLenForTwoString = 210;
    private int textMaxStringCountForTwoString = 6;

    private int titleMaxLenForThreeString = 51;
    private int textMaxLenForThreeString = 140;
    private int textMaxStringCountForThreeString = 4;

    @Override
    public void initialize(StoryFramesValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Составной метод, который вызывает вспомогательные методы валидации.
     *
     * @param object предполагаемый StoryFramesDto, который нужно валидировать.
     * @param constraintValidatorContext
     * @return True/False в зависимости от результата валидации.
     */
    @Override
    public boolean isValid(StoryFramesDto object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return false;
        }

        StoryFramesDto storyFramesDto = (StoryFramesDto) object;

        log.info("getTitle().length() = {}, getText().length() = {}, text.count() = {}",
                storyFramesDto.getTitle().length(),
                storyFramesDto.getText().length(),
                Arrays.stream(storyFramesDto.getText().split("\n")).count());

        return validButtonLink(storyFramesDto) && validTitleText(storyFramesDto);
    }

    /**
     * Вспомогательный метод для проверки полей связанных с LINK, BUTTON и NONE.
     *
     * @param object проверяемый StoryFramesDto.
     * @return True/False в зависимости от результата валидации.
     */
    private boolean validButtonLink(StoryFramesDto object) {
        if (object == null) {
            throw new IllegalArgumentException("@StringValid only applies to StoryFramesDto objects");
        }

        StoryFramesDto storyFramesDto = (StoryFramesDto) object;
        String visibleType = storyFramesDto.getVisibleLinkOrButtonOrNone();

        if (visibleType.equals("LINK")) {
            return !(storyFramesDto.getLinkText().isBlank()) &&
                    !(storyFramesDto.getLinkUrl().isBlank());
        } else if (visibleType.equals("BUTTON")) {
            return !(storyFramesDto.getButtonText().isBlank()) &&
                    !(storyFramesDto.getButtonUrl()).isBlank() &&
                    !(storyFramesDto.getButtonTextColor().isBlank()) &&
                    !(storyFramesDto.getButtonBackgroundColor().isBlank());
        } else if (visibleType.equals("NONE")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Вспомогательный метод для проверки Title и Text.
     *
     * @param object проверяемый StoryFramesDto.
     * @return True/False в зависимости от результата валидации.
     */
    private boolean validTitleText(StoryFramesDto object) {
        if (object == null) {
            throw new IllegalArgumentException("@StringValid only applies to StoryFramesDto objects");
        }

        StoryFramesDto storyFramesDto = (StoryFramesDto) object;
        int countString = (int) Arrays.stream(storyFramesDto.getTitle().split("\n")).count();

        if (countString == 1) {
            return storyFramesDto.getTitle().length() <= titleMaxLenForOneString &&
                    storyFramesDto.getText().length() <= textMaxLenForOneString &&
                    Arrays.stream(storyFramesDto.getText().split("\n")).count() <= textMaxStringCountForOneString;
        } else if (countString == 2) {
            return storyFramesDto.getTitle().length() <= titleMaxLenForTwoString &&
                    storyFramesDto.getText().length() <= textMaxLenForTwoString &&
                    Arrays.stream(storyFramesDto.getText().split("\n")).count() <= textMaxStringCountForTwoString;
        } else if (countString == 3) {
            return storyFramesDto.getTitle().length() <= titleMaxLenForThreeString &&
                    storyFramesDto.getText().length() <= textMaxLenForThreeString &&
                    Arrays.stream(storyFramesDto.getText().split("\n")).count() <= textMaxStringCountForThreeString;
        } else {
            return false;
        }
    }
}
