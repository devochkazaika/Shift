package ru.cft.shiftlab.contentmaker.util.validation.annotation;

import ru.cft.shiftlab.contentmaker.util.validation.validator.BankIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Валидация идентификатора банка.
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = BankIdValidator.class)
public @interface WhiteListValid {
    String message() default "{ru.cft.shiftlab.contentmaker.validation.annotation.WhitelistValid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
