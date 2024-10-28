package ru.cft.shiftlab.contentmaker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;

import java.util.List;

@Getter
@Setter
@Builder
public class ChangedStoryListDto {
    StoryPresentation story;
    List<StoryPresentation> changes;
}
