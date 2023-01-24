package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StoriesRequestDto {

    @JsonProperty("stories")
    @Valid
    private ArrayList<StoriesDto> storiesDtos;
}
