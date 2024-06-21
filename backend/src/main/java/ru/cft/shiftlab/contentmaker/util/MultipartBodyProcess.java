package ru.cft.shiftlab.contentmaker.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.client.MultipartBodyBuilder;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Обработчик для картинок
 */
@Log4j2
public class MultipartBodyProcess {
    public static void addImageInBuilderMultipart(String picture, MultipartBodyBuilder multipartBodyBuilder){
        File file = new File(picture);
        try {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            multipartBodyBuilder.part(file.getName(), new ByteArrayResource(fileBytes));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new StaticContentException("Сервер не может найти нужное изображение = " + picture, "404");
        }
    }
    public static void addJsonInBuilderMultipart(String json, MultipartBodyBuilder multipartBodyBuilder){
        multipartBodyBuilder.part("json", json);
    }
}
