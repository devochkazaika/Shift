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
     * Метод проверяет входит ли bankId в список разрешенных идентификаторов.
     *
     * @param object предполагаемый bankId, который нужно валидировать.
     * @param constraintValidatorContext
     * @return True/False в зависимости от результата валидации.
     */
    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return false;
        }

        String bankId = (String) object;

        return whiteList.checkContainsKeyOrNot(bankId);
    }

}
