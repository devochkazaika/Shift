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
     * @param previewTitle заголовок истории (в будущем будет заменено на bankId).
     * @return название JSON файла.
     */
    public static String createFileName(String previewTitle) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date) + "-" + previewTitle + ".json";
    }
}
