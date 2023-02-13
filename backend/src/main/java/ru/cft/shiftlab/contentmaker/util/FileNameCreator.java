package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, предназначенный для генерации названия JSON файла.
 */

@Component
@RequiredArgsConstructor
public class FileNameCreator {

    private final WhiteList whiteList;

    /**
     * Метод, который генерирует название JSON файла.
     *
     * @param bankId уникальный идентификатор банка, который будет использоваться в формировании названия файла.
     * @return название JSON файла.
     */
    public String createFileName(String bankId) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bankName = whiteList.findValueByKey(bankId);

        return bankName + "_" + bankId + "_" + dateFormat.format(date) + ".json";
    }
}
