package ru.cft.shiftlab.contentmaker.validation.Impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.validation.StoriesMultipleTitleTextValid;

import java.util.Arrays;
@Slf4j

@EnableConfigurationProperties(StoriesTitleTextValidator.class)
@ConfigurationProperties(prefix = "title.text.validator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoriesTitleTextValidator implements ConstraintValidator<StoriesMultipleTitleTextValid, StoryFramesDto> {

    private int titleMaxLenForOneString;
    private int textMaxLenForOneString;
    private int textMaxStringCountForOneString;


    private int titleMaxLenForTwoString;
    private int textMaxLenForTwoString;
    private int textMaxStringCountForTwoString;

    private int titleMaxLenForThreeString;
    private int textMaxLenForThreeString;
    private int textMaxStringCountForThreeString;

    @Override
    public void initialize(StoriesMultipleTitleTextValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(StoryFramesDto object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            throw new IllegalArgumentException("@StringValid only applies to StoryFramesDto objects");
        }

        int countString = (int) Arrays.stream(((StoryFramesDto) object).getTitle().split("/n")).count();

        log.info("getTitle().length() = {}, getText().length() = {}, text.count() = {}",
                ((StoryFramesDto) object).getTitle().length(),
                ((StoryFramesDto) object).getText().length(),
                Arrays.stream(((StoryFramesDto) object).getText().split("/n")).count());

        return switch (countString) {
            case 1 -> ((StoryFramesDto) object).getTitle().length() <= titleMaxLenForOneString &&
                    ((StoryFramesDto) object).getText().length() <= textMaxLenForOneString &&
                    Arrays.stream(((StoryFramesDto) object).getText().split("/n")).count() <= textMaxStringCountForOneString;
            case 2 -> ((StoryFramesDto) object).getTitle().length() <= titleMaxLenForTwoString &&
                    ((StoryFramesDto) object).getText().length() <= textMaxLenForTwoString &&
                    Arrays.stream(((StoryFramesDto) object).getText().split("/n")).count() <= textMaxStringCountForTwoString;
            case 3 -> ((StoryFramesDto) object).getTitle().length() <= titleMaxLenForThreeString &&
                    ((StoryFramesDto) object).getText().length() <= textMaxLenForThreeString &&
                    Arrays.stream(((StoryFramesDto) object).getText().split("/n")).count() <= textMaxStringCountForThreeString;
            default -> false;
        };
    }
}
