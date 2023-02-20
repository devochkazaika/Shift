package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, предназначенный для конверации StoriesRequestDto в StoryPresentation
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {

    private final ModelMapper modelMapper;

    /**
     * Метод конвертирующий исходный DTO, прилетающий с FrontEnd'а, в Entity,
     * которая является итоговым JSON-файлом.
     *
     * @param storiesRequestDto исходное DTO, которое мы получаем от FrontEnd'a.
     * @param jsonDirectory директория, в которую будет записан итоговый JSON.
     * @param picturesDirectory директория, в которую будут записаны картинки из DTO.
     * @return ассоциативный массив, который содержит: ключ - "stories", значение - StoryPresentation.
     */
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

    /**
     * Метод для конвертации StoryDto в StoryPresentation.
     *
     * @param bankId идентификатор банка, который отправил запрос.
     * @param storyDto DTO, которую нужно конвертировать в Entity.
     * @param jsonDirectory директория, в которую будет записан итоговый JSON.
     * @param picturesDirectory директория, в которую будут записаны картинки из DTO.
     * @return StoryPresentation, полученный после конвертации StoryDto.
     */
    public StoryPresentation fromStoryDtoToStoryPresentation(String bankId,
                                                             StoryDto storyDto,
                                                             String jsonDirectory,
                                                             String picturesDirectory) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);
        storyPresentation.setBankId(bankId);

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

    /**
     * Метод для конвертации StoryFramesDto в StoryPresentationFrames.
     *
     * @param storyFramesDto DTO, которую нужно конвертировать в Entity.
     * @return StoryPresentationFrames, полученный после конвертации из StoryFramesDto.
     */
    public StoryPresentationFrames fromStoryFramesDtoToStoryPresentationFrames(StoryFramesDto storyFramesDto) {
        StoryPresentationFrames storyPresentationFrames = modelMapper.map(storyFramesDto, StoryPresentationFrames.class);

        log.info("New StoryPresentationFrames Object: {}",
                storyPresentationFrames);

        return storyPresentationFrames;
    }

}
