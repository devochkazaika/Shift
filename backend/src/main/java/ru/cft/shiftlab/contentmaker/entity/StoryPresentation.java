package ru.cft.shiftlab.contentmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoryPresentation {
    /**
     * Идентификатор истории.
     */
    private Long id;

    /**
     * Код банка.
     */
    private String bankId;

    /**
     * Размер шрифта.
     */
    @JsonProperty("font-size")
    private String fontSize;

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
     * Список карточек истории.
     */
    @JsonProperty("storyFrames")
    private ArrayList<StoryPresentationFrames> storyPresentationFrames = new ArrayList<>();

}
