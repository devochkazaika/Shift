package ru.cft.shiftlab.contentmaker.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;

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
            try {
                var list = mapper.readValue(new File(FILES_SAVE_DIRECTORY, fileName), typeReference);
                storyPresentationList = (list.containsKey(STORIES)) ?
                        list.get(STORIES) :
                        new ArrayList<StoryPresentation>();
            }
            catch (Exception e){
                return new ArrayList<StoryPresentation>();
            }
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

    public File checkDirectoryBankAndPlatformIsExist(String bankId, String platform){
        File directory = new File(FILES_SAVE_DIRECTORY + "/" + bankId + "/" + platform);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new StaticContentException("Directory does not exist or is not a directory: " + directory.getAbsolutePath(),
                    "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        return directory;
    }

    public void createFolders(String picturesSaveDirectory) throws IOException {
        File newDirectory = new File(picturesSaveDirectory);
        if (!newDirectory.exists()) {
            if(!newDirectory.mkdirs()){
                throw new IOException("Can't create dir: " + picturesSaveDirectory);
            }
        }
    }
    public void deleteFolders(String picturesSaveDirectory) throws IOException {
        FileUtils.deleteDirectory(new File(picturesSaveDirectory));
    }
}
