package ru.cft.shiftlab.contentmaker.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StoriesDto {
    //Ограничение количества строк?
    @NotBlank(message = "The preview title is not specified")
    private String previewTitle;

    @NotBlank(message = "Preview color not specified")
    private String previewTitleColor;

    @Null
    private byte[] previewUrl;

//    @Valid
    @Size(max = 6, message = "")
    @NotEmpty
    private ArrayList<StoryFramesDto> storyFramesDtos;
}
