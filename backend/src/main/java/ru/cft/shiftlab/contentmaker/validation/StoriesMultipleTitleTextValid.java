package ru.cft.shiftlab.contentmaker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.shiftlab.contentmaker.validation.Impl.StoriesTitleTextValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StoriesTitleTextValidator.class)
@Documented
public @interface StoriesMultipleTitleTextValid {
    String message() default "{ru.cft.shiftlab.contentmaker.validation.MultipleTitleTextValid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
