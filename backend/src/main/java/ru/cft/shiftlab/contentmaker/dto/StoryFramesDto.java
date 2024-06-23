package ru.cft.shiftlab.contentmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.StoryFramesValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * DTO, который содержит информацию о карточке истории.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@StoryFramesValid(message = "Incorrect parameters for Story Frames")
public class StoryFramesDto {

    /**
     * Подзаголовок карточки истории.
     */
    @NotBlank(message = "The story title is not specified")
    private String title;

    /**
     * Текст карточки истории.
     */
    @NotBlank(message = "The text of the story is not specified")
    private String text;

    /**
     * Цвет текста карточки истории.
     */
    @NotBlank(message = "Story color not specified")
    private String textColor;

    /**
     * Поле, показывающее необходимость либо ссылки, либо кнопки, либо ничего из этого.
     */
    @NotBlank(message = "Button and link display is not specified")
    @Pattern(regexp = "BUTTON|NONE", message = "Incorrect parameters. Possible: BUTTON, NONE")
    private String visibleLinkOrButtonOrNone;

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
    @NotBlank(message = "The gradient for the story is not specified")
    @Pattern(regexp = "EMPTY|HALF|FULL", message = "Incorrect parameters. Possible: EMPTY, HALF, FULL")
    private String gradient;

}