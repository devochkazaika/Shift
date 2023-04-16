package ru.cft.shiftlab.contentmaker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

/**
 * Сущность, которая содержит информацию об итоговом JSON-файле.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoryPresentation {

    /**
     * Код банка.
     */
    private String bankId;

    /**
     * Заголовок превью.
     */
    private String previewTitle;

    /**
     * Цвет заголовка превью.
     */
    private String previewTitleColor;

    /**
     * Путь до картинки для превью.
     */
    private String previewUrl;

    /**
     * Градиент превью.
     */
    private String previewGradient;

    /**
     * Директория, в которой хранится JSON файл банка.
     */
    private String jsonDirectory;

    /**
     * Директория, в которой хранятся необходимые для банка картинки.
     */
    private String picturesDirectory;

    /**
     * Список карточек истории.
     */
    @JsonProperty("storyFrames")
    private ArrayList<StoryPresentationFrames> storyPresentationFrames = new ArrayList<>();

}
