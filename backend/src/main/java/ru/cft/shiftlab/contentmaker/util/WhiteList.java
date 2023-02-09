package ru.cft.shiftlab.contentmaker.util;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Класс, предназначенный для работы с Map списка банков.
 */
public class WhiteList {

    static Map<String, String> whitelistBank = Map.ofEntries(
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

    /**
     * Метод, который возвращает true/false в зависимости от того, есть ли в Map ключ с таким значением.
     *
     * @param bankId уникальный идентификатор банка.
     * @return true - если ключ с таким значением имеется, false - если нет.
     */
    public static boolean checkContainsKeyOrNot(String bankId) {
        return whitelistBank.containsKey(bankId);
    }

    /**
     * Метод, который возвращает название банка по его уникальному идентификатору.
     *
     * @param bankId уникальный идентификатор банка.
     * @return название банка.
     */
    public static String findValueByKey(String bankId) {
        String bankName = whitelistBank.get(bankId);
        bankName = bankName.replace("\"", "");
        return bankName;
    }


}
