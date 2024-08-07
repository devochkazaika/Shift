package ru.cft.shiftlab.contentmaker.service;

import ru.cft.shiftlab.contentmaker.entity.History;

import java.util.List;

public interface HistoryServiceStories {
    List<History> getStoryHistory(Long id);
    List<History> getBankAndPlatformHistory(String bankId, String platform);

    void deleteHistoryByStoryId(Long id);
//    List<History> getHeadHistory();
}
