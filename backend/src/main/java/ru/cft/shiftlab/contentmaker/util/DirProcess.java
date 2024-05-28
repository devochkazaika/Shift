package ru.cft.shiftlab.contentmaker.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.STORIES;

@Component
public class DirProcess {
    private List<StoryPresentation> checkStoriesFileInBankDir(String fileName) throws IOException {
        List<StoryPresentation> storyPresentationList = new ArrayList<StoryPresentation>();
        File bankJsonFile = new File(FILES_SAVE_DIRECTORY + fileName);

        if (bankJsonFile.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, List<StoryPresentation>>> typeReference = new TypeReference<>() {
            };

            storyPresentationList.addAll(mapper.readValue(
                            new File(FILES_SAVE_DIRECTORY,fileName), typeReference)
                    .get(STORIES)
            );
        }
        return storyPresentationList;
    }
    @SuppressWarnings ("unchecked")
    public <T> List<T> checkFileInBankDir(String fileName, String type) throws IOException {
        if (type.equals(STORIES)) {
            return (List<T>) checkStoriesFileInBankDir(fileName);
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
