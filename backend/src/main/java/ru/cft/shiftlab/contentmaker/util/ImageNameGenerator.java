package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Класс, предназначенный для генерации уникального наименования изображения.
 */

@Component
@RequiredArgsConstructor
public class ImageNameGenerator {

    /**
     * Метод, который генерирует уникальное наименование изображения.
     *
     * @return наименование изображения.
     */
    public String generateImageName() {
//        try{
//            throw new Exception("сделать проверку на имя картинки");
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        String uuid = UUID.randomUUID().toString();
        String imageName = uuid.replaceAll("-", "");
        imageName = imageName.replaceAll("[^\\p{L}\\d_\\.-]", "");
        return imageName;
    }

}
