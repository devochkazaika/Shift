package ru.cft.shiftlab.contentmaker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shiftlab.contentmaker.validation.StoryFramesValid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@StoryFramesValid(message = "Incorrect parameters for Story Frames")
public class StoryFramesDto {
    @NotBlank(message = "The story title is not specified")
    private String title;

    @NotBlank(message = "The text of the story is not specified")
    private String text;

    @NotBlank(message = "Story color not specified")
    private String textColor;

    @NotEmpty(message = "The picture of the story is not specified")
    private byte[] pictureUrl;

    @NotBlank(message = "Button and link display is not specified")
    @Pattern(regexp = "LINK|BUTTON|NONE", message = "Incorrect parameters. Possible: LINK, BUTTON, NONE")
    private String visibleLinkOrButtonOrNone;

    private String linkText;
    private String linkUrl;
    private String buttonText;
    private String buttonTextColor;
    private String buttonBackgroundColor;
    private String buttonUrl;

    @NotBlank(message = "The gradient for the story is not specified")
    @Pattern(regexp = "EMPTY|HALF|FULL", message = "Incorrect parameters. Possible: EMPTY, HALF, FULL")
    private String gradient;
}