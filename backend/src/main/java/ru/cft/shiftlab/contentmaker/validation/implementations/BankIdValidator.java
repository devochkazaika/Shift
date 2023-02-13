package ru.cft.shiftlab.contentmaker.validation.implementations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.cft.shiftlab.contentmaker.util.WhiteList;
import ru.cft.shiftlab.contentmaker.validation.WhitelistValid;


@Slf4j
@Data
public class BankIdValidator implements ConstraintValidator<WhitelistValid, String> {

    private final WhiteList whiteList;

    @Autowired
    public BankIdValidator(WhiteList whiteList) {
        this.whiteList = whiteList;
    }

    @Override
    public void initialize(WhitelistValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            throw new IllegalArgumentException("@StringValid only applies to StoryFramesDto objects");
        }

        String bankId = (String) object;

        log.info("storiesRequestDto from bank with bankId = {}",
                bankId);

        return whiteList.checkContainsKeyOrNot(bankId);
    }

}
