package ru.cft.shiftlab.contentmaker.util;

import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;

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
     * @throws StaticContentException исключение, которое возникает при невозможности извлечь расширение файла.
     */
    public String getFileExtensionFromByteArray(byte[] bytes) throws StaticContentException {
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            return mimeType.split("/")[1];
        }
        catch (IOException e) {
            throw new StaticContentException("Невозможно извлечь расширение файла", "500");
        }
    }

}
