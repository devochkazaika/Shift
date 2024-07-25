package ru.cft.shiftlab.contentmaker.util.validation.validator;

import ru.cft.shiftlab.contentmaker.util.validation.annotation.UUIDValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<UUIDValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(String.format("UUID = %s is not valid", value));
        }
    }
}