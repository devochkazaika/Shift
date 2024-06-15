package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Класс, предназначенный для генерации названия JSON файла.
 */

@Component
@RequiredArgsConstructor
public class FileNameCreator {
    public enum PictureTypes{
        PICTURE,
        ICON
    }

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
    public static void renameOld(String picturesSaveDirectory, long lastId){
        File file = new File(picturesSaveDirectory);
        File[] files = file.listFiles();
        Stream.of(files)
                .filter(x ->x.getName().startsWith(String.valueOf(lastId)))
                .forEach(x -> {
                    File newFile = new File(x.getParent(), x.getName().substring(0, x.getName().indexOf('.')) + "_old"+x.getName().substring(x.getName().indexOf('.')));
                    boolean b = x.renameTo(newFile);
                    if (!b){
                        newFile.delete();
                        x.renameTo(newFile);
                    }
                });
    }

}
