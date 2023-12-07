package ru.cft.shiftlab.contentmaker.util.validation.validator.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Проверка текста на размер строк и их количество
 */
public class checkTextConditional {
    static public boolean checkTextCond(String text, int textMaxStringCountForThreeString, int maxLength) {
        var textLines = text.split("\n");
        var countLines = StringUtils.countMatches(text, "\n") + 1;

        return countLines < textMaxStringCountForThreeString &&
                Arrays.stream(textLines).noneMatch(entry -> entry.length() > maxLength);
    }
}
