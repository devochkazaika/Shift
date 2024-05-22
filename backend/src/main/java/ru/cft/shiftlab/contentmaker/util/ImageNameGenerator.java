package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
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
    public String generateImageName(String picturesSaveDirectory) {
        ArrayList<String> listOfNamesPic = new ArrayList<>();
        File file = new File(picturesSaveDirectory);
        File[] files = file.listFiles();
        boolean isNotUnique;
        String uuid;
        do{
            isNotUnique = false;
            uuid = UUID.randomUUID().toString();
            for(File pic : files){
                String fileNameWithOutExt = FilenameUtils.removeExtension(pic.getName());
                if(fileNameWithOutExt.equals(uuid)){
                    isNotUnique = true;
                }
            }
        } while(isNotUnique);

        String imageName = uuid.replaceAll("-", "");
        imageName = imageName.replaceAll("[^\\p{L}\\d_\\.-]", "");
        return imageName;
    }

}
