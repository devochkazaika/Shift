package ru.cft.shiftlab.contentmaker.service;

import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;

import java.io.IOException;

/**
 * Интерфейс, предназначенный для сохранения файлов в определенную директорию.
 */
public interface FileSaverService {

    /**
     * Метод для сохранения JSON файла и картинки в определенную директорию.
     *
     * @param storiesRequestDto DTO с информацией о Stories.
     * @param testOrNot         булевая переменная, которая указывает на то, вызывается метод в тесте или нет.
     * @throws RuntimeException исключение, которые может возникнуть во время работы приложения.
     */
    default void saveFiles(String storiesRequestDto, MultipartFile[] images, boolean testOrNot) {
        IOException e = new IOException();
        throw new RuntimeException(e);
    }
}
