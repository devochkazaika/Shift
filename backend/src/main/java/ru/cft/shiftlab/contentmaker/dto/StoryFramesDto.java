package ru.cft.shiftlab.contentmaker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shiftlab.contentmaker.validation.StoriesMultipleTitleTextValid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@StoriesMultipleTitleTextValid(message = "Wrong format in the title or text")
public class StoryFramesDto {

    @NotBlank(message = "The story title is not specified")
    private String title;

    @NotBlank(message = "The text of the story is not specified")
    private String text;

    @NotBlank(message = "Story color not specified")
    private String textColor;

    @NotEmpty(message = "The photo of the story is not specified")
    private byte[] pictureUrl;

    @NotBlank(message = "The hyperlink text is not specified")
    private String linkText;

    @NotBlank(message = "The hyperlink address is not specified")
    private String linkUrl;

    @NotNull(message = "Button visibility is not specified")
    private Boolean buttonVisible;

    @NotBlank(message = "The button text is not specified") //?
    private String buttonText;

    @NotBlank(message = "The color of the button text is not specified") //?
    private String buttonTextColor;

    @NotBlank(message = "The button background color is not specified")
    private String buttonBackgroundColor;

    @NotBlank(message = "The link for the button is not specified")
    private String buttonUrl;

    @NotBlank(message = "The gradient for the story is not specified")
    @Pattern(regexp = "EMPTY|HALF|FULL", message = "Incorrect parameters. Possible: EMPTY, HALF, FULL")
    private String gradient;
}