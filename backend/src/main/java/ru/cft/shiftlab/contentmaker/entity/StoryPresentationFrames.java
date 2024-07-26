package ru.cft.shiftlab.contentmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Вспомогательная сущность для хранения данных о каждой карточке.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "frames")
public class StoryPresentationFrames {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    /**
     * Подзаголовок карточки истории.
     */
    private String title;

    /**
     * Текст карточки истории.
     */
    private String text;

    /**
     * Цвет текста карточки истории.
     */
    private String textColor;

    /**
     * Путь до картинки для карточки истории.
     */
    private String pictureUrl;

    /**
     * Поле, показывающее необходимость либо ссылки, либо кнопки, либо ничего из этого.
     */
    private String visibleButtonOrNone;

    /**
     * Текст кнопки.
     */
    private String buttonText;

    /**
     * Цвет текста кнопки.
     */
    private String buttonTextColor;

    /**
     * Цвет фона кнопки.
     */
    private String buttonBackgroundColor;

    /**
     * Ссылка и на внутрянку МП, и во внешку.
     */
    private String buttonUrl;

    /**
     * Градиент карточки истории.
     */
    private String gradient;

    @ManyToOne
    @JoinColumn(name = "story_id")
    @JsonIgnore
    private StoryPresentation story;

}
