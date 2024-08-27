package ru.cft.shiftlab.contentmaker.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;

@Component
public class Constants {
    public static final String STORIES = "stories";
    @Value("${file_path_to_save.stories}")
    private String filesSaveDirectory;
    @Value("${file_path_to_save.banners}")
    private String bannersDirectory;

    public static String FILES_SAVE_DIRECTORY;
    public static String BANNERS_SAVE_DIRECTORY;

    @PostConstruct
    public void init() {
        FILES_SAVE_DIRECTORY = filesSaveDirectory;
        BANNERS_SAVE_DIRECTORY = bannersDirectory;
    }
    public static final String FILES_TEST_DIRECTORY =
            System.getProperty("user.dir") + "/src/test/java/ru/cft/shiftlab/contentmaker/test_pictures/";

    public static final Integer MAX_COUNT_FRAME = 6;
}
