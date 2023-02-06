package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * DTO, который приходит с FrontEnd'а
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoriesRequestDto {

    @NotBlank
    private String bankId;

    @JsonProperty("stories")
    @Valid
    private ArrayList<StoryDto> storyDtos = new ArrayList<>();

    public void addStoriesDtos(StoryDto storyDto) {
        this.storyDtos.add(storyDto);
    }
}
