package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shiftlab.contentmaker.validation.StoryValid;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@StoryValid(message = "Incorrect number of lines in the title")
public class StoryDto {
    @NotBlank(message = "The preview title is not specified")
    private String previewTitle;

    @NotBlank(message = "Preview color not specified")
    private String previewTitleColor;

    @NotEmpty(message = "The preview of the story is not specified")
    private byte[] previewUrl;

    @NotBlank(message = "The gradient for the preview is not specified")
    @Pattern(regexp = "EMPTY|FULL", message = "Incorrect parameters. Possible: EMPTY, FULL")
    private String previewGradient;

    @JsonProperty("storyFrames")
    @Valid
    @Size(min = 1, max = 6, message = "The maximum number of stories is 6")
    @NotEmpty
    private ArrayList<StoryFramesDto> storyFramesDtos = new ArrayList<>();

    public void addStoryFramesDto(StoryFramesDto storyFramesDto) {
        this.storyFramesDtos.add(storyFramesDto);
    }
}
