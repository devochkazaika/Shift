package ru.cft.shiftlab.contentmaker.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.cft.shiftlab.contentmaker.util.Constants.*;

@RequiredArgsConstructor
@Component
public class StoryMapper extends ObjectMapper {
    {
        enable(SerializationFeature.INDENT_OUTPUT);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final DtoToEntityConverter dtoToEntityConverter;
    private final DirProcess dirProcess;
    public void putStoryToJson(List<StoryPresentation> storyPresentationList, String bankId, String platform) throws IOException {
        Map<String, List<StoryPresentation>> resultMap = new HashMap<>();
        resultMap.put(STORIES, storyPresentationList);
        File file = new File(FILES_SAVE_DIRECTORY, FileNameCreator.createJsonName(bankId, platform));
        writeValue(file, resultMap);
    }
    public void putStoryToJson(StoryPresentation storyPresentation, String bankId, String platform) throws IOException {
        final var storyList = getStoryList(bankId, platform);
        if (storyList.size() + 1 >= MAX_COUNT_FRAME) throw new IllegalArgumentException("Cant save a frame. The maximum size is reached");
        storyList.add(storyPresentation);
        putStoryToJson(storyList, bankId, platform);
    }
    /**
     * Метод возвращает конкретную историю
     */
    public StoryPresentation getStoryModel(List<StoryPresentation> storyPresentationList,
                                           Long id) throws IOException {
        return storyPresentationList.stream()
                .filter(x-> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find the story with id=" + id));
    }

    public List<StoryPresentation> getStoryList(String bankId, String platform) throws IOException {
        List<StoryPresentation> list = dirProcess.checkFileInBankDir(
                FileNameCreator.createJsonName(bankId, platform),
                STORIES);
        return (list == null) ? new ArrayList<StoryPresentation>() : list;
    }

}
