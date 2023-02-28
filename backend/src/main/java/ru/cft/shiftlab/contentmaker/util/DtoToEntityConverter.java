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
 * Класс, предназначенный для конвертации StoriesRequestDto в StoryPresentation
 */
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {

    private final ModelMapper modelMapper;

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
                                                             String picturesDirectory,
                                                             String previewUrl) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);
        storyPresentation.setBankId(bankId);

        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            storyPresentation.setBankId(bankId);
            storyPresentation.setJsonDirectory(jsonDirectory);
            storyPresentation.setPicturesDirectory(picturesDirectory);
            storyPresentation.getStoryPresentationFrames().add(fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto));
            storyPresentation.setPreviewUrl(previewUrl);
        }

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

        return storyPresentationFrames;
    }

}
