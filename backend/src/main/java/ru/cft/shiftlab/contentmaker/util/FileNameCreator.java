package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
     * @param platformType тип платформы.
     * @return название JSON файла.
     */
    public static String createFileName(String bankId, String platformType) {
        if (Objects.equals(platformType, "IOS")) {
            return "story_" + bankId + "_iOS" + ".json";
        }
        else if (Objects.equals(platformType, "ANDROID")) {
            return "story_" + bankId + "_android" + ".json";
        }
        else if (Objects.equals(platformType, "WEB")) {
            return "story_" + bankId + "_web" + ".json";
        }
        return "story_" + bankId + ".json";
    }
    public static void createFolders(String picturesSaveDirectory) throws IOException {
        File newDirectory = new File(picturesSaveDirectory);
        if (!newDirectory.exists()) {
            if(!newDirectory.mkdirs()){
                throw new IOException("Can't create dir: " + picturesSaveDirectory);
            }
        }
    }

}
