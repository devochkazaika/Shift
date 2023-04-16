package ru.cft.shiftlab.contentmaker.util.validation.validator;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import ru.cft.shiftlab.contentmaker.util.WhiteList;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.WhiteListValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Имплементация валидации идентификатора банка.
 */
@Data
public class BankIdValidator implements ConstraintValidator<WhiteListValid, String> {

    private final WhiteList whiteList;

    @Autowired
    public BankIdValidator(WhiteList whiteList) {
        this.whiteList = whiteList;
    }

    @Override
    public void initialize(WhiteListValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Метод, проверяющий является ли переданный объект допустимым банковским идентификатором..
     *
     * @param object предполагаемый идентификатор банка, который необходимо проверить на допустимость.
     * @param constraintValidatorContext контекст, содержащий информацию о проверке.
     * @return {@code true}, если переданный объект является допустимым банковским идентификатором,
     * {@code false} в противном случае.
     */
    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return false;
        }

        return whiteList.checkContainsKeyOrNot(object);
    }

}
