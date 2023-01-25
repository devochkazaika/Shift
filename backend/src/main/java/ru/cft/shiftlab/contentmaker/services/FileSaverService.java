package ru.cft.shiftlab.contentmaker.services;

import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;

import java.io.IOException;

/**
 * Интерфейс, предназанченный для сохранения файлов в определенную директорию.
 */
public interface FileSaverService {

    /**
     * Метод для сохранения JSON файла и картинки в определенную директорию.
     *
     * @param storiesRequestDto DTO с информацией о Stories.
     * @throws RuntimeException исключение, которые может возникнуть во время выполнения приложения.
     */
    default void saveFiles(StoriesRequestDto storiesRequestDto) {
        IOException e = new IOException();
        throw new RuntimeException(e);
    }
}
