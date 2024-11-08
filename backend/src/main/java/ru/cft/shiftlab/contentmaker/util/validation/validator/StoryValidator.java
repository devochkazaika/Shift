package ru.cft.shiftlab.contentmaker.util.validation.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.StoryValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static ru.cft.shiftlab.contentmaker.util.validation.validator.util.checkTextConditional.checkTextCond;


/**
 * Имплементация валидации StoryDto.
 */
@EnableConfigurationProperties(StoryFramesValidator.class)
@ConfigurationProperties(prefix = "preview.title.validator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryValidator implements ConstraintValidator<StoryValid, StoryDto> {
    private int titleMaxStringCount = 3;
    private int titleMaxStringLength = 17;

    @Override
    public void initialize(StoryValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Метод проверяет количество строк в поле PreviewTitle.
     *
     * @param object предполагаемый StoryDto, который нужно валидировать.
     * @param constraintValidatorContext контекст, содержащий информацию о проверке.
     * @return True/False в зависимости от результата валидации.
     */
    @Override
    public boolean isValid(StoryDto object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return false;
        }
        String textTitle = object.getPreviewTitle();
        return checkTextCond(textTitle, titleMaxStringCount, titleMaxStringLength);
    }
}
