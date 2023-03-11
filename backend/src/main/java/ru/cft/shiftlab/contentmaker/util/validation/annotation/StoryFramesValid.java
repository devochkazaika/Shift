package ru.cft.shiftlab.contentmaker.util.validation.annotation;

import ru.cft.shiftlab.contentmaker.util.validation.validator.StoryFramesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Валидация StoryFramesDto.
 */
@Target({ ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StoryFramesValidator.class)
@Documented
public @interface StoryFramesValid {
    String message() default "{ru.cft.shiftlab.contentmaker.validation.annotation.StoryFramesValid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
