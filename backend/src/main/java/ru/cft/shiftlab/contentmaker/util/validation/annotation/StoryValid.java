package ru.cft.shiftlab.contentmaker.util.validation.annotation;

import ru.cft.shiftlab.contentmaker.util.validation.validator.StoryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации {@link ru.cft.shiftlab.contentmaker.dto.StoryDto}.
 */
@Target({ ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StoryValidator.class)
@Documented
public @interface StoryValid {
    String message() default "{ru.cft.shiftlab.contentmaker.validation.annotation.StoryValid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
