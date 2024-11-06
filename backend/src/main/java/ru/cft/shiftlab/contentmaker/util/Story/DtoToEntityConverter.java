package ru.cft.shiftlab.contentmaker.util.Story;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Класс, предназначенный для конвертации StoriesRequestDto в StoryPresentation.
 */
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {
    private final ModelMapper modelMapper;
    private final MultipartFileToImageConverter multipartFileToImageConverter;

    public StoryPresentation fromStoryRequestDtoToStoryPresentation(StoriesRequestDto storyDto) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto.getStoryDtos().get(0), StoryPresentation.class);
        storyPresentation.setBankId(storyDto.getBankId());
        storyPresentation.setPlatform(storyDto.getPlatform());
        ArrayList<StoryPresentationFrames> frames = (ArrayList<StoryPresentationFrames>) storyDto.getStoryDtos().get(0).getStoryFramesDtos().stream()
                .map(this::fromStoryFramesDtoToStoryPresentationFrames).collect(Collectors.toList());
        storyPresentation.setStoryPresentationFrames(frames);
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

    public Banner fromBannerDtoToBanner(BannerDto bannerDto,
                                        String pictureUrl,
                                        String iconUrl){
        Banner banner = modelMapper.map(bannerDto, Banner.class);
        banner.setBankName(bannerDto.getBankName());
        banner.setPicture(pictureUrl);
        banner.setIcon(iconUrl);
        return banner;
    }

}
