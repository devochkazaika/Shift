package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shiftlab.contentmaker.validation.StoryTitleValid;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@StoryTitleValid(message = "Incorrect number of lines in the title")
public class StoriesDto {

    @NotBlank(message = "The preview title is not specified")
    private String previewTitle;

    @NotBlank(message = "Preview color not specified")
    private String previewTitleColor;

    private byte[] previewUrl;

    @NotBlank(message = "The gradient for the preview is not specified")
    @Pattern(regexp = "EMPTY|FULL", message = "Incorrect parameters. Possible: EMPTY, FULL")
    private String previewGradient;

    @JsonProperty("storyFrames")
    @Valid
    @Size(max = 6, message = "The maximum number of stories is 6")
    @NotEmpty
    private ArrayList<StoryFramesDto> storyFramesDtos;
}
