package ru.cft.shiftlab.contentmaker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

/**
 * Entity, которая содержит информацию об итоговом JSON-файле.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoryPresentation {

    private String bankId;
    private String previewTitle;
    private String previewTitleColor;
    private String previewUrl;
    private String previewGradient;

    private String jsonDirectory;
    private String picturesDirectory;

    @JsonProperty("storyFrames")
    private ArrayList<StoryPresentationFrames> storyPresentationFrames = new ArrayList<>();
}
