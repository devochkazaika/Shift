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
@EnableConfigurationProperties(TitleTextValidator.class)
@ConfigurationProperties(prefix = "title.text.validator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleTextValidator implements ConstraintValidator<StoriesMultipleTitleTextValid, StoryFramesDto> {

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
        if (!(object instanceof StoryFramesDto)) {
            throw new IllegalArgumentException("@StringValid only applies to StoryFramesDto objects");
        }

        StoryFramesDto storyFramesDto = (StoryFramesDto) object;

        int countString = (int) Arrays.stream(storyFramesDto.getTitle().split("/n")).count();

        log.info("getTitle().length() = {}, getText().length() = {}, text.count() = {}",
                storyFramesDto.getTitle().length(),
                storyFramesDto.getText().length(),
                Arrays.stream(storyFramesDto.getText().split("/n")).count());

        switch (countString) {
            case 1:
                return (storyFramesDto.getTitle().length() <= titleMaxLenForOneString &&
                        storyFramesDto.getText().length() <= textMaxLenForOneString &&
                        Arrays.stream(storyFramesDto.getText().split("/n")).count() <= textMaxStringCountForOneString) ?
                        true : false;
            case 2:
                return (storyFramesDto.getTitle().length() <= titleMaxLenForTwoString &&
                        storyFramesDto.getText().length() <= textMaxLenForTwoString &&
                        Arrays.stream(storyFramesDto.getText().split("/n")).count() <= textMaxStringCountForTwoString) ?
                        true : false;
            case 3:
                return (storyFramesDto.getTitle().length() <= titleMaxLenForThreeString &&
                        storyFramesDto.getText().length() <= textMaxLenForThreeString &&
                        Arrays.stream(storyFramesDto.getText().split("/n")).count() <= textMaxStringCountForThreeString) ?
                        true : false;
            default:
                return false;
        }
    }
}
