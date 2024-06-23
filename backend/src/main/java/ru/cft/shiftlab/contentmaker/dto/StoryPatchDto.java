package ru.cft.shiftlab.contentmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryPatchDto {
    /**
     * Заголовок превью.
     */
    @NotBlank(message = "The preview title is not specified")
    private String previewTitle;

    /**
     * Цвет текста заголовка превью.
     */
    @NotBlank(message = "Preview color not specified")
    private String previewTitleColor;

    /**
     * Градиент превью.
     */
    @NotBlank(message = "The gradient for the preview is not specified")
    @Pattern(regexp = "EMPTY|HALF|FULL", message = "Incorrect parameters. Possible: EMPTY, HALF, FULL")
    private String previewGradient;
}
