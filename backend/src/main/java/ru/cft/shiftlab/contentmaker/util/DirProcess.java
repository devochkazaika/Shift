package ru.cft.shiftlab.contentmaker.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.STORIES;

@Component
@Slf4j
@AllArgsConstructor
public class DirProcess {
    @Autowired
    private final Constants constants;
    private List<StoryPresentation> checkStoriesFileInBankDir(String fileName) throws IOException {
        List<StoryPresentation> storyPresentationList = new ArrayList<StoryPresentation>();
        File bankJsonFile = new File(constants.FILES_SAVE_DIRECTORY + fileName);
        System.out.println(bankJsonFile.getAbsolutePath());
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

    public List<List<String>> getBankIdAndPlatform(){
        File directory = new File(FILES_SAVE_DIRECTORY + "/");
        File[] files = directory.listFiles(File::isFile);
        List<List<String>> listBankAndPlatfor = new ArrayList<>();
        for (var i : files){
            try {
                String[] word = i.getName().split("_");
                listBankAndPlatfor.add(Arrays.asList(word[1], word[2]));
            }
            catch (Exception e){
                log.error("could not import file with name = " + i.getName(), e);
            }
        }
        return listBankAndPlatfor;
    }
}
