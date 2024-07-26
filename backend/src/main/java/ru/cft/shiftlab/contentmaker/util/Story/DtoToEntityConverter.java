package ru.cft.shiftlab.contentmaker.util.Story;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationFramesRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationRepository;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;

import java.io.IOException;
import java.util.LinkedList;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.MAX_COUNT_FRAME;

/**
 * Класс, предназначенный для конвертации StoriesRequestDto в StoryPresentation.
 */
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {

    private final ModelMapper modelMapper;
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final StoryPresentationRepository storyPresentationRepository;
    private final StoryPresentationFramesRepository storyPresentationFramesRepository;

    /**
     * Метод для конвертации StoryDto в StoryPresentation.
     *
     * @param bankId            уникальный идентификатор банка, который отправил запрос.
     * @param storyDto          DTO, которую нужно конвертировать в Entity.
     * @return StoryPresentation, полученный после конвертации StoryDto.
     */
    public StoryPresentation fromStoryDtoToStoryPresentation(String bankId,
                                                             StoryDto storyDto,
                                                             String previewUrl) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);
        storyPresentation.setBankId(bankId);

        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            storyPresentation.getStoryPresentationFrames()
                    .add(fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto));
        }
        storyPresentation.setPreviewUrl(previewUrl);

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
        storyPresentation = storyPresentationRepository.save(storyPresentation);
        String filePath = FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/";

        //сама история
        String previewUrl = multipartFileToImageConverter.parsePicture(
                new ImageContainer(frameUrl.removeFirst()),
                filePath,
                storyPresentation.getId());
        storyPresentation.setPreviewUrl(previewUrl);

        //карточки
        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            var frame = fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto);
            frame.setPictureUrl(multipartFileToImageConverter.parsePicture(
                    new ImageContainer(frameUrl.removeFirst()),
                    FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/",
                    storyPresentation.getId(),
                    frame.getId()));
            storyPresentation.getStoryPresentationFrames()
                    .add(frame);
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

}
