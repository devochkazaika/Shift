package ru.cft.shiftlab.contentmaker.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
/**
 * Класс, предназначенный для конверации StoriesRequestDto в StoryPresentation
 */
public class DtoToEntityConverter {

    private final ModelMapper modelMapper;

    public Map<String, List<StoryPresentation>> fromStoriesRequestDtoToMap(StoriesRequestDto storiesRequestDto,
                                                                           String jsonDirectory,
                                                                           String picturesDirectory) {

        Map<String, List<StoryPresentation>> presentationMap = new HashMap<>();

        List<StoryPresentation> storyPresentations = new ArrayList<>();

        for (StoryDto storyDto: storiesRequestDto.getStoryDtos()) {
            storyPresentations.add(fromStoryDtoToStoryPresentation(storiesRequestDto.getBankId(), storyDto,
                    jsonDirectory, picturesDirectory));
            presentationMap.put("stories", storyPresentations);
        }

        return presentationMap;
    }

    public StoryPresentation fromStoryDtoToStoryPresentation(String bankId,
                                                             StoryDto storyDto,
                                                             String jsonDirectory,
                                                             String picturesDirectory) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);

        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            storyPresentation.setBankId(bankId);
            storyPresentation.setJsonDirectory(jsonDirectory);
            storyPresentation.setPicturesDirectory(picturesDirectory);
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
