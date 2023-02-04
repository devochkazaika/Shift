package ru.cft.shiftlab.contentmaker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNameCreator {

    public static String createFileName(String previewTitle) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date) + "-" + previewTitle + ".json";
    }
}
