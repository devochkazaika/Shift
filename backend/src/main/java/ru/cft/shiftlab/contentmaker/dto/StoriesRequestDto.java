package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.PlatformValid;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.WhiteListValid;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

/**
 * Класс, представляющий DTO с информацией, которая прилетает с Frontend'a.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class StoriesRequestDto {

    /**
     * Код банка.
     */
    @NotBlank(message = "bankId is not specified")
    @WhiteListValid(message = "bankId must match the allowed")
    private String bankId;

    @NotBlank(message = "platformType is not specified")
    @PlatformValid
    private String platformType;

    /**
     * Информация об историях.
     */
    @JsonProperty("stories")
    @Valid
    private ArrayList<StoryDto> storyDtos = new ArrayList<>();

}
