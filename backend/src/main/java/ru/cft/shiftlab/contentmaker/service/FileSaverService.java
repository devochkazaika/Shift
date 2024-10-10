package ru.cft.shiftlab.contentmaker.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;

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
    default StoryPresentation saveFiles(String strStoriesRequestDto, MultipartFile previewImage, MultipartFile[] images) throws IOException {
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
    default ResponseEntity<?> deleteService(String bankId, String platform, Long id) throws Throwable {
        IOException e = new IOException();
        throw new RuntimeException(e);
    }

    /**
     * Метод, возвращающий список всех историй банка и платформы
     *
     * @param bankId Имя банка
     * @param platform Тип платформы
     * @return List of stories
     * @throws IOException
     */
    HttpEntity<List<StoryPresentation>> getFilePlatformJson(String bankId, String platform) throws IOException;

    StoryPresentation getStory(Long id);
    /**
     * Метод, возвращающий все непринятые истории
     *
     * @return List of unApproved stories
     */
    List<StoryPresentation> getUnApprovedStories();

    List<StoryPresentation> getUnApprovedStories(String bankId, String platform);

    /**
     * Метод, возвращающий все удаленные из JSON истории
     *
     * @return Get Deleted stories
     */
    List<StoryPresentation> getDeletedStories();
    List<StoryPresentation> getDeletedStories(String bankId, String platform);

    /**
     * Метод, восстанавливающий удаленную историю из бд
     *
     * @param id Id истории
     * @throws IOException
     */
    void restoreStory(Long id) throws IOException;

    /**
     * Изменение общих параметров истории, а именно previewTitle, previewTitleColor, previewGradient
     *
     * @param storiesRequestDto StoryDto + platform + bankID
     * @param bankId Банк
     * @param platform Платформа
     * @param id id истории
     * @throws IOException
     */
    StoryPresentation changeStory(String storiesRequestDto, MultipartFile file, String bankId, String platform, Long id) throws IOException;

    /**
     * Метод для изменения карточки в историях
     *
     * @param storyFramesRequestDto StoryFramesDto
     * @param bankId Банк
     * @param platform Платформа
     * @param id id истории
     * @param frameId UUID карточки
     * @throws IOException
     */
    void changeFrameStory(String storyFramesRequestDto,
                          MultipartFile file,
                          String bankId,
                          String platform,
                          Long id,
                          String frameId) throws IOException;
    /**
     * Метод, предназначенный для удаления одной карточки из историй.
     *
     * @param bankId   Банк
     * @param platform Платформа
     * @param id       id истории
     * @param frameId  UUID карточки
     */
    ResponseEntity<?> deleteStoryFrame(String bankId, String platform, String id, String frameId) throws Throwable;

    /**
     * Метод для добавления карточки к истории
     *
     * @param storyFramesDto Dto новой карточки
     * @param file Картинка новой карточки
     * @param bankId Имя банка
     * @param platform Тип платформы (ALL PLATFORMS | ANDROID | IOS | WEB)
     * @param id Id истории
     * @return Новую карточку
     * @throws IOException
     */
    StoryPresentationFrames addFrame(String storyFramesDto, MultipartFile file,
                                     String bankId, String platform, Long id) throws IOException;


    /**
     * Метод для перестановки карточек
     *
     * @param id Id истории
     * @param bankId Имя банка
     * @param platform Платформа
     * @throws IOException
     */
    void swapFrames(Long id, String bankId, String platform, List<String> newOrder) throws IOException;

    /**
     * Метод для одобрения истории новой истории
     *
     * @param bankId Имя банка
     * @param platform Платформа
     * @param id Id истории
     * @throws IOException
     */
    StoryPresentation approveStory(String bankId, String platform, Long id) throws IOException;

    /**
     * Метод для удаления навсегда истории. Удаления из БД
     * @param bankId Имя банка
     * @param platform Платформа
     * @param id Id истории
     * @return
     * @throws Throwable
     */
    ResponseEntity<?> deleteStoriesFromDb(String bankId, String platform, Long id) throws Throwable;

    List<StoryPresentation> getChangedRequest(String bank, String platform);

    StoryPresentation updatePreview(Long changing, Long changeable);

}
