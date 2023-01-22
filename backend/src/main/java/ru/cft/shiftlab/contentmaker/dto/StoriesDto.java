package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.cft.shiftlab.contentmaker.validation.StoryTitleValid;

import java.util.ArrayList;

@Data
@StoryTitleValid(message = "The number of lines in the title should be less than " +
        "${preview.title.validator.titleMaxStringCount}")
public class StoriesDto {
    //Ограничение количества строк?
    @NotBlank(message = "The preview title is not specified")
    private String previewTitle;

    @NotBlank(message = "Preview color not specified")
    private String previewTitleColor;

    private byte[] previewUrl;

    @JsonProperty("storyFrames")
    @Valid
    @Size(max = 6, message = "The maximum number of stories is 6")
    @NotEmpty
    private ArrayList<StoryFramesDto> storyFramesDtos;
}
