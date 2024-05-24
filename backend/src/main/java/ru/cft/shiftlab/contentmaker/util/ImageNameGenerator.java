package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Класс, предназначенный для генерации уникального наименования изображения.
 */

@Component
@RequiredArgsConstructor
public class ImageNameGenerator {

    /**
     * Метод, который генерирует уникальное наименование изображения.
     * В случае когда изображение уже есть, его имя меняется на {имя}_old.{формат}
     *
     * @return наименование изображения.
     */
    public String generateImageName(String picturesSaveDirectory, Long id) {
        File file = new File(picturesSaveDirectory);
        File[] files = file.listFiles();
        String uuid = (id == null) ? "0" : id.toString();
        AtomicInteger count = new AtomicInteger();
        Stream.of(files)
                .filter(x->
                    x.getName().startsWith(uuid) && !x.getName().substring(0, x.getName().indexOf('.')).endsWith("_old"))
                .forEach(x -> count.incrementAndGet());

        String imageName = uuid;
        return imageName+"_"+count;
    }

}
