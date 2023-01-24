package ru.cft.shiftlab.contentmaker.validation.Impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cft.shiftlab.contentmaker.dto.StoriesDto;
import ru.cft.shiftlab.contentmaker.validation.StoryTitleValid;

import java.util.Arrays;


@Slf4j
@EnableConfigurationProperties(StoriesTitleTextValidator.class)
@ConfigurationProperties(prefix = "preview.title.validator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryTitleValidator implements ConstraintValidator<StoryTitleValid, StoriesDto> {

    private int titleMaxStringCount;

    @Override
    public void initialize(StoryTitleValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(StoriesDto object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            throw new IllegalArgumentException("@StringValid only applies to StoryFramesDto objects");
        }

        StoriesDto storiesDto = (StoriesDto) object;

        return Arrays.stream(storiesDto.getPreviewTitle().split("/n")).count() <= titleMaxStringCount;
    }
}
