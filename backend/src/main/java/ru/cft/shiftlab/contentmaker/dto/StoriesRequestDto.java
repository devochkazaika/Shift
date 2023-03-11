package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.WhitelistValid;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

/**
 * Исходная DTO, которая приходит с FrontEnd'а
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoriesRequestDto {

    @NotBlank(message = "bankId is not specified")
    @WhitelistValid(message = "bankId must match the allowed")
    private String bankId;

    @JsonProperty("stories")
    @Valid
    private ArrayList<StoryDto> storyDtos = new ArrayList<>();

}
