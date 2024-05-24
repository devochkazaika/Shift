package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.STORIES;

public final class JsonHandler {
    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature
                        .FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }
    public List<StoryPresentation> writeImagesAndJson(String fileName,
                                    MultipartFile previewImage,
                                    MultipartFile[] images,
                                    StoriesRequestDto storiesRequestDto,
                                    String bankId,
                                    String picturesSaveDirectory) throws IOException {
        List<StoryPresentation> storyPresentationList = new ArrayList<>();
        checkFileInBankDir(fileName, storyPresentationList);
        return storyPresentationList;

    }
    public void checkFileInBankDir(String fileName, List<StoryPresentation> storyPresentationList) throws IOException {
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
    }
}
