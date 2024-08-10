package ru.cft.shiftlab.contentmaker.util.validation.annotation;

import ru.cft.shiftlab.contentmaker.util.validation.validator.UUIDValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RUNTIME)
@Constraint(validatedBy = UUIDValidator.class)
public @interface UUIDValid {
    String message() default "{ru.cft.shiftlab.contentmaker.validation.annotation.UUIDValidator}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}