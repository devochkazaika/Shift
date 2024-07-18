package ru.cft.shiftlab.contentmaker.util.Image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

/**
 * Класс, предназначенный для генерации уникального наименования изображения.
 */

@Component
@RequiredArgsConstructor
public class ImageNameGenerator {

    private String getFileExtension(String picturesSaveDirectory){
        File file = new File(picturesSaveDirectory);
        return file.getName().split(".")[1];
    }

    /**
     * Метод, который генерирует уникальное наименование изображения.
     * В случае когда изображение уже есть, его имя меняется на {имя}_old.{формат}
     *
     * @return наименование изображения.
     */
    public String generateImageName(String picturesSaveDirectory, Long id, UUID uuid) {
        return id + "_" + uuid.toString();
    }
    public String generateImageName(String picturesSaveDirectory, Long id) {
        return id+"_"+"0";
    }


}
