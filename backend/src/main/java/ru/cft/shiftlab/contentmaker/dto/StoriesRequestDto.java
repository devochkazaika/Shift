package ru.cft.shiftlab.contentmaker.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StoriesRequestDto {
//    @Valid
    private ArrayList<StoriesDto> storiesDtos;
}
