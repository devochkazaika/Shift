package ru.cft.shiftlab.contentmaker.util;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;

@Slf4j
/**
 * Класс, предназначенный для конверации StoriesRequestDto в StoryPresentation
 */
public class RequestDtoToEntityConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public RequestDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StoryPresentation fromStoriesRequestDtoToStoryPresentation(StoryDto storyDto) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);

        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            storyPresentation.getStoryPresentationFrames().add(fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto));

        }

        log.info("New StoryPresentation Object: {}",
                storyPresentation);


        return storyPresentation;
    }

    public StoryPresentationFrames fromStoryFramesDtoToStoryPresentationFrames(StoryFramesDto storyFramesDto) {
        StoryPresentationFrames storyPresentationFrames = modelMapper.map(storyFramesDto, StoryPresentationFrames.class);

        log.info("New StoryPresentationFrames Object: {}",
                storyPresentationFrames);

        return storyPresentationFrames;
    }
}
