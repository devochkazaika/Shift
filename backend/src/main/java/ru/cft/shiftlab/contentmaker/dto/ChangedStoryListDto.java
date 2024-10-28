package ru.cft.shiftlab.contentmaker.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ChangedStoryListDto {
    StoryPresentation story;
    List<StoryWithHistoryId> changes;
}
