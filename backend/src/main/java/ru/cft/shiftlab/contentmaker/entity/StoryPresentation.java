package ru.cft.shiftlab.contentmaker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Entity, которая содержит информацию о итоговом json
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryPresentation {

    private String bankId;
    private String previewTitle;
    private String previewTitleColor;
    private byte[] previewUrl;
    private String previewGradient;

    private String jsonDirectory;
    private String picturesDirectory;

    @JsonProperty("storyFrames")
    private ArrayList<StoryPresentationFrames> storyPresentationFrames = new ArrayList<>();
}
