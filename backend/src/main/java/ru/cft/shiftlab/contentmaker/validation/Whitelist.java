package ru.cft.shiftlab.contentmaker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.shiftlab.contentmaker.validation.implementations.BankIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BankIdValidator.class)
public @interface Whitelist {
    String message() default "{ru.cft.shiftlab.contentmaker.validation.Whitelist}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
