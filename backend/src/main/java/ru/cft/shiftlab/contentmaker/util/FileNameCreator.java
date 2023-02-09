package ru.cft.shiftlab.contentmaker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, предназначенный для генерации названия JSON файла.
 */
public class FileNameCreator {

    /**
     * Метод, который генерирует название JSON файла.
     *
     * @param bankId уникальный идентификатор банка, который будет использоваться в формировании названия файла.
     * @return название JSON файла.
     */
    public static String createFileName(String bankId) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bankName = WhiteList.findValueByKey(bankId);

        return bankName + "_" + bankId + "_" + dateFormat.format(date) + ".json";
    }
}
