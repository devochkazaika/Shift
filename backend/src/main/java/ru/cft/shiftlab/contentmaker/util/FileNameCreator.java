package ru.cft.shiftlab.contentmaker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, предназначенный для генерации названия JSON файла.
 */

@Component
public class FileNameCreator {

    private final WhiteList whiteList;

    @Autowired
    public FileNameCreator(WhiteList whiteList) {
        this.whiteList = whiteList;
    }

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
