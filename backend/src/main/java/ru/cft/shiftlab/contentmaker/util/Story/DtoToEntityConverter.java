package ru.cft.shiftlab.contentmaker.util.Story;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;

/**
 * Класс, предназначенный для конвертации StoriesRequestDto в StoryPresentation.
 */
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {

    private final ModelMapper modelMapper;
    private final MultipartFileToImageConverter multipartFileToImageConverter;

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
                                                             Long id,
                                                             LinkedList<MultipartFile> frameUrl) throws IOException {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);
        storyPresentation.setBankId(bankId);
        String filePath = FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/";

        String previewUrl = multipartFileToImageConverter.parsePicture(
                new ImageContainer(frameUrl.removeFirst()),
                filePath,
                id);

        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            var frame = fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto);
            frame.setPictureUrl(multipartFileToImageConverter.parsePicture(
                    new ImageContainer(frameUrl.removeFirst()),
                    FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/",
                    id));
            storyPresentation.getStoryPresentationFrames()
                    .add(frame);
        }
        storyPresentation.setPreviewUrl(previewUrl);

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
        storyPresentationFrames.setId(UUID.randomUUID());
        return storyPresentationFrames;
    }

}
