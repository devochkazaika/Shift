package ru.cft.shiftlab.contentmaker.dto;

import lombok.*;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoryWithHistoryId{
    StoryPresentation storyPresentation;
    Long historyId;
}