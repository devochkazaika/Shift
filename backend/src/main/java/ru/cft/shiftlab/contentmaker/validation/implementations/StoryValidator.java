package ru.cft.shiftlab.contentmaker.validation.implementations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.validation.StoryValid;

import java.util.Arrays;


/**
 * Имплементация валидации StoryDto.
 */
@Slf4j
@EnableConfigurationProperties(StoryFramesValidator.class)
@ConfigurationProperties(prefix = "preview.title.validator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryValidator implements ConstraintValidator<StoryValid, StoryDto> {
    private int titleMaxStringCount = 2;

    @Override
    public void initialize(StoryValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Метод проверяет количество строк в поле PreviewTitle.
     *
     * @param object предполагаемый StoryDto, который нужно валидировать.
     * @param constraintValidatorContext
     * @return True/False в зависимости от результата валидации.
     */
    @Override
    public boolean isValid(StoryDto object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            throw new IllegalArgumentException("@StringValid only applies to StoryFramesDto objects");
        }

        StoryDto storyDto = (StoryDto) object;

        return Arrays.stream(storyDto.getPreviewTitle().split("\n")).count() <= titleMaxStringCount;
    }
}
