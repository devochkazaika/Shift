package ru.cft.shiftlab.contentmaker.service;

import ru.cft.shiftlab.contentmaker.dto.ChangedStoryListDto;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;

import java.io.IOException;
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

    void approveCreateStory(Long historyId) throws IOException;

    List<HistoryEntity> getCreateRequestByUser();

    List<ChangedStoryListDto> getUnApprovedChangedStories(String bankId, String platform);

    void deleteChangingRequest(Long idOperation);
}
