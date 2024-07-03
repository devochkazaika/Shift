package ru.cft.shiftlab.contentmaker.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import java.io.IOException;
import java.util.List;

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
    /**
     * Метод для удаления JSON файла и картинок.
     *
     * @param bankId Имя банка
     * @param platform Тип платформы
     * @param id Id истории
     */
    default ResponseEntity<?> deleteService(String bankId, String platform, String id) throws Throwable {
        IOException e = new IOException();
        throw new RuntimeException(e);
    }
    /**
     * Метод для удаления JSON файла и картинок.
     *
     * @param bankId Имя банка
     * @param platform Тип платформы
     */
    default HttpEntity<MultiValueMap<String, HttpEntity<?>>> getFilePlatform(String bankId, String platform) throws IOException {
        IOException e = new IOException();
        throw new RuntimeException(e);
    }
    public HttpEntity<List<StoryPresentation>> getFilePlatformJson(String bankId, String platform) throws IOException;

    void changeStory(String storiesRequestDto, MultipartFile file, String bankId, String platform, Long id) throws IOException;
    void changeFrameStory(String storyFramesRequestDt,
                          String bankId,
                          String platform,
                          Long id,
                          String frameId,
                          MultipartFile file) throws IOException;
    ResponseEntity deleteStoryFrame(String bankId, String platform, String id, String frameId) throws Throwable;
}
