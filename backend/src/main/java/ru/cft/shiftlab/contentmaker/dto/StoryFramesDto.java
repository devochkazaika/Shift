package ru.cft.shiftlab.contentmaker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.cft.shiftlab.contentmaker.validation.StoriesMultipleTitleTextValid;

@Data
@StoriesMultipleTitleTextValid(message = "The title and the main text must comply with the rules: " +
        "1)" +
        "Title 1 line - " + "${title.text.validator.titleMaxLenForOneString}" + " characters;" +
        "The main text is 7 lines - " + "${title.text.validator.textMaxLenForOneString}" + " characters;" +
        "2)" +
        "Title 2 line - " + "${title.text.validator.titleMaxLenForTwoString}" + " characters; " +
        "The main text is 6 lines -  " + "${title.text.validator.textMaxLenForTwoString}" + " characters; " +
        "3) " +
        "Title 3 line - " + "${title.text.validator.titleMaxLenForThreeString}" + " characters; " +
        "Main text 4 lines - " + "${title.text.validator.textMaxLenForThreeString}" + " characters;")
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
}