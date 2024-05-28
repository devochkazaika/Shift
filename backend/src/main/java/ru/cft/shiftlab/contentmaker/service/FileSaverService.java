package ru.cft.shiftlab.contentmaker.service;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Интерфейс, предназначенный для сохранения файлов в определенную директорию.
 */
public interface FileSaverService {

    /**
     * Метод для сохранения JSON файла и картинки в определенную директорию.
     *
     * @param strStoriesRequestDto DTO с информацией о Stories.
     * @param previewImage Картинка для preview
     * @param images Массив картинок для карточек
     * @throws RuntimeException исключение, которые может возникнуть во время работы приложения.
     */
    default void saveFiles(String strStoriesRequestDto, MultipartFile previewImage, MultipartFile[] images) {
        IOException e = new IOException();
        throw new RuntimeException(e);
    }
    default void deleteService(String bankId, String platform, String id) throws Exception  {
        IOException e = new IOException();
        throw new RuntimeException(e);
    }
    default HttpEntity<MultiValueMap<String, HttpEntity<?>>> getFilePlatform(String bankId, String platform){
        IOException e = new IOException();
        throw new RuntimeException(e);
    }
}
