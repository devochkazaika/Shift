package ru.cft.shiftlab.contentmaker.entity;

import lombok.*;

/**
 * Вспомогательная сущность для хранения данных о каждой карточке.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoryPresentationFrames {

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
    private String visibleLinkOrButtonOrNone;

    /**
     * Текст гиперссылки.
     */
    private String linkText;

    /**
     * Ссылка на внешний источник.
     */
    private String linkUrl;

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

}
