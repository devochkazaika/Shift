package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoriesRequestDto {

    @JsonProperty("stories")
    @Valid
    private ArrayList<StoriesDto> storiesDtos = new ArrayList<>();

    public void addStoriesDtos(StoriesDto storiesDto) {
        this.storiesDtos.add(storiesDto);
    }
}
