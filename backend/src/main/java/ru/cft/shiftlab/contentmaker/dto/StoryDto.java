package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.StoryValid;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * DTO, который содержит информацию о превью.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@StoryValid(message = "Incorrect number of lines in the title")
public class StoryDto {

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
    @Size(min = 1, max = 6, message = "The maximum number of stories is 6")
    @NotEmpty
    private ArrayList<StoryFramesDto> storyFramesDtos = new ArrayList<>();

}
