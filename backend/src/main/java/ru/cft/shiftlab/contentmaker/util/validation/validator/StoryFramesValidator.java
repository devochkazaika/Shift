package ru.cft.shiftlab.contentmaker.util.validation.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.StoryFramesValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Имлпементация валидации StoryFramesDto.
 */
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
     * @param object                     предполагаемый StoryFramesDto, который нужно валидировать.
     * @param constraintValidatorContext контекст, в котором вычисляется ограничение.
     * @return True/False в зависимости от результата валидации.
     */
    @Override
    public boolean isValid(StoryFramesDto object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return false;
        }

        StoryFramesDto storyFramesDto = (StoryFramesDto) object;

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
            return false;
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
        } else return visibleType.equals("NONE");
    }

    /**
     * Вспомогательный метод для проверки Title и Text.
     *
     * @param object проверяемый StoryFramesDto.
     * @return True/False в зависимости от результата валидации.
     */
    private boolean validTitleText(StoryFramesDto object) {
        if (object == null) {
            return false;
        }

        StoryFramesDto storyFramesDto = (StoryFramesDto) object;
        var countLines = StringUtils.countMatches(storyFramesDto.getTitle(), "\n");

        if (countLines == 1) {
            return storyFramesDto.getTitle().length() <= titleMaxLenForOneString &&
                    storyFramesDto.getText().length() <= textMaxLenForOneString &&
                    StringUtils.countMatches(storyFramesDto.getTitle(), "\n") < textMaxStringCountForOneString;
        } else if (countLines == 2) {
            return storyFramesDto.getTitle().length() <= titleMaxLenForTwoString &&
                    storyFramesDto.getText().length() <= textMaxLenForTwoString &&
                    StringUtils.countMatches(storyFramesDto.getTitle(), "\n") < textMaxStringCountForTwoString;
        } else if (countLines == 3) {
            return storyFramesDto.getTitle().length() <= titleMaxLenForThreeString &&
                    storyFramesDto.getText().length() <= textMaxLenForThreeString &&
                    StringUtils.countMatches(storyFramesDto.getTitle(), "\n") < textMaxStringCountForThreeString;
        } else {
            return false;
        }
    }
}
