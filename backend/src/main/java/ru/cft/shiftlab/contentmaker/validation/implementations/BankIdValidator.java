package ru.cft.shiftlab.contentmaker.validation.implementations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.validation.Whitelist;

import java.util.Map;
import static java.util.Map.entry;


@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankIdValidator implements ConstraintValidator<Whitelist, String> {
    Map<String, String> whitelistBank = Map.ofEntries(
            entry("absolutbank", "АКБ \"Абсолют Банк\" (ПАО)"),
            entry("agroros","АО \"БАНК \"АГРОРОС\""),
            entry("akcept", "АО \"БАНК АКЦЕПТ\""),
            entry("alexbank", "ПАО БАНК \"АЛЕКСАНДРОВСКИЙ\""),
            entry("apkbank", "АО КБ \"АГРОПРОМКРЕДИТ\""),
            entry("bankdolinsk", "КБ \"Долинск\" (АО)"),
            entry("chelindbank", "ПАО \"ЧЕЛИНДБАНК\""),
            entry("expobank", "АО \"Экспобанк\""),
            entry("gaztransbank", "ООО КБ \"ГТ БАНК\""),
            entry("iturupbank", "Банк \"ИТУРУП\" (ООО)"),
            entry("jtbank", "Джей энд Ти Банк (АО)"),
            entry("lanta", "АКБ \"Ланта-Банк\" (АО)"),
            entry("novobank", "ПАО УКБ \"НОВОБАНК\""),
            entry("nskbl", "БАНК \"ЛЕВОБЕРЕЖНЫЙ\" (ПАО)"),
            entry("rostfinance", "ООО КБ \"РОСТФИНАНС\""),
            entry("sdm", "СДМ-Банк (ПАО)"),
            entry("siab", "ПАО БАНК \"СИАБ\""),
            entry("solidbank", "АО \"Солид Банк\""),
            entry("tavrich", "Таврический Банк (АО)"),
            entry("tkbbank", "ТКБ БАНК ПАО"),
            entry("tpsb", "ПАО \"ТОМСКПРОМСТРОЙБАНК\""),
            entry("venets-bank", "АО БАНК \"ВЕНЕЦ\""),
            entry("vlbb", "АО \"ВЛАДБИЗНЕСБАНК\""),
            entry("wildberries", "ООО \"Вайлдберриз Банк\"")
    );

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

        return whitelistBank.containsKey(bankId);
    }

}
