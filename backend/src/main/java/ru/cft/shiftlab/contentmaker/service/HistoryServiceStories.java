package ru.cft.shiftlab.contentmaker.service;

import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;

import java.util.List;

public interface HistoryServiceStories{
    List<HistoryEntity> getStoryHistory(Long id);
    List<HistoryEntity> getBankAndPlatformHistory(String bankId, String platform);

    void deleteHistoryByStoryId(Long id);
//    List<History> getHeadHistory();

    List<HistoryEntity> getHistoryByBankAndPlatform(String bank, String platform);

    List<HistoryEntity> getAllHistory();

    List<HistoryEntity> getRequestByUser();

    void rollBack(Long idHistory);
}
