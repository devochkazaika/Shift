package ru.cft.shiftlab.contentmaker.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Класс, предназначенный для извлечения расширения картинки из байтогово массива.
 */
public class FileExtensionExtractor {

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
