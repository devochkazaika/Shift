package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Класс, предназначенный для генерации названия JSON файла.
 */

@Component
@RequiredArgsConstructor
public class FileNameCreator {

    /**
     * Метод, который генерирует название JSON файла.
     *
     * @param bankId уникальный идентификатор банка, который будет использоваться в формировании названия файла.
     * @return название JSON файла.
     */
    public String createFileName(String bankId) {
        return "story_" + bankId + ".json";
    }

}
