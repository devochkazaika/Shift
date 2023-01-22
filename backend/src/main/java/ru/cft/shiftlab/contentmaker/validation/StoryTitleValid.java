package ru.cft.shiftlab.contentmaker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.shiftlab.contentmaker.validation.Impl.StoryTitleValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StoryTitleValidator.class)
@Documented
public @interface StoryTitleValid {
    String message() default "{ru.cft.shiftlab.contentmaker.validation.PreviewTitleValid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
