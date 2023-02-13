package ru.cft.shiftlab.contentmaker.util;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Класс, предназначенный для извлечения расширения картинки из байтового массива.
 */
@Component
public class FileExtensionExtractor {

    /**
     * Метод, который извлекает расширение картинки из байтового массива.
     *
     * @param bytes байтовый массив, из которого будет доставаться расширение файла (картинки).
     * @throws RuntimeException исключение, которое возникает в процессе работы программы.
     */
    public String getFileExtensionFromByteArray(byte[] bytes) {
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            return mimeType.split("/")[1];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
