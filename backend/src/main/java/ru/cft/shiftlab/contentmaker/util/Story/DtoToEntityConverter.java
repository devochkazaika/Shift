package ru.cft.shiftlab.contentmaker.util.Story;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationFramesRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationRepository;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.MAX_COUNT_FRAME;

/**
 * Класс, предназначенный для конвертации StoriesRequestDto в StoryPresentation.
 */
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {

    private final BannerRepository bannerRepository;
    private final ModelMapper modelMapper;
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final StoryPresentationRepository storyPresentationRepository;
    private final StoryPresentationFramesRepository storyPresentationFramesRepository;

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
     * Метод для конвертации StoryDto в StoryPresentation.
     *
     * @param bankId            уникальный идентификатор банка, который отправил запрос.
     * @param storyDto          DTO, которую нужно конвертировать в Entity.
     * @return StoryPresentation, полученный после конвертации StoryDto.
     */
    public StoryPresentation fromStoryDtoToStoryPresentation(StoryDto storyDto) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);
        return storyPresentation;
    }

    public StoryPresentation fromStoryDtoToStoryPresentation(String bankId,
                                                             String platform,
                                                             StoryDto storyDto,
                                                             LinkedList<MultipartFile> frameUrl) throws IOException {
        //проверка на максимальное допустимое число карточек в истории
        var countStoryFrames = storyDto.getStoryFramesDtos().size();
        if (countStoryFrames == 0 || countStoryFrames > MAX_COUNT_FRAME){
            throw new IllegalArgumentException("Bad count of the story frames");
        }

        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);
        storyPresentation.setBankId(bankId);
        storyPresentation.setPlatform(platform);
        String filePath = FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/";

        //сама история
        String previewUrl = multipartFileToImageConverter.parsePicture(
                new ImageContainer(frameUrl.removeFirst()),
                filePath,
                storyPresentation.getId());
        storyPresentation.setPreviewUrl(previewUrl);

        //карточки
        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            var frame = modelMapper.map(storyFramesDto, StoryPresentationFrames.class);
            frame.setPictureUrl(multipartFileToImageConverter.parsePicture(
                    new ImageContainer(frameUrl.removeFirst()),
                    FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/",
                    storyPresentation.getId(),
                    frame.getId()));
            storyPresentation.getStoryPresentationFrames().add(frame);
            frame.setStory(storyPresentation);
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
        return storyPresentationFramesRepository.save(storyPresentationFrames);
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
