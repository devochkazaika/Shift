package ru.cft.shiftlab.contentmaker.validation.implementations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.util.WhiteList;
import ru.cft.shiftlab.contentmaker.validation.Whitelist;

import java.util.Map;
import static java.util.Map.entry;


@Slf4j
@Data
public class BankIdValidator implements ConstraintValidator<Whitelist, String> {
    @Override
    public void initialize(Whitelist constraintAnnotation) {
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

        return WhiteList.checkContainsKeyOrNot(bankId);
    }

}
