package ru.cft.shiftlab.contentmaker.util;

import java.nio.file.Paths;

public class Constants {
    public static final String STORIES = "stories";
    public static final String FILES_SAVE_DIRECTORY =
            String.valueOf(Paths.get("backend/src/main/resources/static").toAbsolutePath())+"/site/share/htdoc/_files/skins/mobws_story/";
//            System.getProperty("user.dir").endsWith("backend") ?
//            System.getProperty("user.dir") + "/src/main/resources/site/share/htdoc/_files/skins/mobws_story/" :
//            System.getProperty("user.dir") + "/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/" ;
//    "/content-maker/backend/src/main/resources/site/share/htdoc/_files/skins/mobws_story/";

    public static final String FILES_TEST_DIRECTORY =
            System.getProperty("user.dir") + "/src/test/java/ru/cft/shiftlab/contentmaker/test_pictures/";

    public static final Integer MAX_COUNT_FRAME = 6;
}
