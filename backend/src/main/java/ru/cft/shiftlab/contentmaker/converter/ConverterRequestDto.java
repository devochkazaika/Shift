package ru.cft.shiftlab.contentmaker.converter;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;

@Slf4j
public class ConverterRequestDto {

    private final ModelMapper modelMapper;

    @Autowired
    public ConverterRequestDto(ModelMapper modelMapper) {
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
