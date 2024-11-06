package ru.cft.shiftlab.contentmaker.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.cft.shiftlab.contentmaker.util.Constants.*;

@RequiredArgsConstructor
@Component
@Log4j2
public class StoryMapper extends ObjectMapper {
    {
        enable(SerializationFeature.INDENT_OUTPUT);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private final DirProcess dirProcess;

    public void putStoryToJson(List<StoryPresentation> storyPresentationList, String bankId, String platform) {
        // Если директория не создана
        dirProcess.createFolders(FILES_SAVE_DIRECTORY+bankId+"/"+platform+"/");
        Map<String, List<StoryPresentation>> resultMap = new HashMap<>();
        resultMap.put(STORIES, storyPresentationList);
        File file = new File(FILES_SAVE_DIRECTORY, FileNameCreator.createJsonName(bankId, platform));
        try{
            writeValue(file, resultMap);
        }
        catch (IOException e){
            log.error("Could not write story to json", e);
            throw new StaticContentException("Could not write story to json");
        }
    }

    public void putStoryToJson(StoryPresentation storyPresentation, String bankId, String platform) {
        final var storyList = getStoryList(bankId, platform);
        if (storyList.size() + 1 >= MAX_COUNT_FRAME) throw new IllegalArgumentException("Cant save a frame. The maximum size is reached");
        var story = storyList.stream()
                .filter(x-> x.getId().equals(storyPresentation.getId()))
                .findFirst().orElse(null);
        if (story == null){
            storyList.add(storyPresentation);
            putStoryToJson(storyList, bankId, platform);
        }
        else{
            throw new StaticContentException("Story with id " + storyPresentation.getId() + " in file is already exist");
        }
    }
    /**
     * Метод возвращает конкретную историю
     */
    public StoryPresentation getStoryModel(List<StoryPresentation> storyPresentationList,
                                           Long id) {
        var story = storyPresentationList.stream()
                .filter(x-> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find the story with id=" + id));
        story.getStoryPresentationFrames().forEach(x -> x.setStory(story));
        return story;
    }

    public List<StoryPresentation> getStoryList(String bankId, String platform) {
        try{
            List<StoryPresentation> list = dirProcess.checkFileInBankDir(
                    FileNameCreator.createJsonName(bankId, platform),
                    STORIES);
            return (list == null) ? new ArrayList<StoryPresentation>() : list;
        }
        catch (IOException e){
            log.error("Could not read story list", e);
            throw new StaticContentException(e.getMessage());
        }
    }

    public StoryPresentation updateStoryEntity(final StoryPresentation main, StoryPresentation change){
        main.setFontSize(change.getFontSize());
        main.setPreviewGradient(change.getPreviewGradient());
        main.setPreviewTitleColor(change.getPreviewTitleColor());
        main.setPreviewTitle(change.getPreviewTitle());
        return main;
    }

    public StoryPresentation deleteStoryFromJson(String bankId, String platform, Long id) {
        //Берем список историй
        List<StoryPresentation> list = getStoryList(bankId, platform);

        //удаляем нужную историю
        StoryPresentation storyDeleted = null;
        for (int i = 0; i < list.size(); i++) {
            storyDeleted = list.get(i);
            if (storyDeleted.getId().equals(id)) {
                list.remove(i);
                break;
            }
        }
        if (storyDeleted == null) throw new IllegalArgumentException(String.format(
                "Could not find the frame with id = %s",
                id)
        );

        //кладем в json
        putStoryToJson(list, bankId, platform);
        return storyDeleted;
    }

    public StoryPresentation map(StoriesRequestDto storyDto) {
        try{
            var strDto = writeValueAsString(storyDto.getStoryDtos().get(0));
            StoryPresentation storyPresentation = readValue(strDto, StoryPresentation.class);
            storyPresentation.setBankId(storyDto.getBankId());
            storyPresentation.setPlatform(storyDto.getPlatform());
            ArrayList<StoryPresentationFrames> frames = (ArrayList<StoryPresentationFrames>) storyDto.getStoryDtos().get(0).getStoryFramesDtos().stream()
                    .map(this::map).collect(Collectors.toList());
            storyPresentation.setStoryPresentationFrames(frames);
            return storyPresentation;
        }
        catch (JsonProcessingException e){
            throw new StaticContentException("Could not convert from dto to Entity");
        }
    }

    /**
     * Метод для конвертации StoryFramesDto в StoryPresentationFrames.
     *
     * @param storyFramesDto DTO, которую нужно конвертировать в Entity.
     * @return StoryPresentationFrames, полученный после конвертации из StoryFramesDto.
     */
    public StoryPresentationFrames map(StoryFramesDto storyFramesDto) {
        try{
            var strDto = writeValueAsString(storyFramesDto);
            return readValue(strDto, StoryPresentationFrames.class);
        }
        catch (JsonProcessingException e){
            throw new StaticContentException(e.getMessage());
        }

    }

}
