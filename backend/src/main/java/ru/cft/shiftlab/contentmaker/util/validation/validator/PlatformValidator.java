package ru.cft.shiftlab.contentmaker.util.validation.validator;

import ru.cft.shiftlab.contentmaker.util.validation.annotation.PlatformValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class PlatformValidator implements ConstraintValidator<PlatformValid, String> {
    public static final Set<String> platforms = Set.of(
            "ANDROID",
            "WEB",
            "IOS",
            "ALL PLATFORMS"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return platforms.contains(value);
    }
}
