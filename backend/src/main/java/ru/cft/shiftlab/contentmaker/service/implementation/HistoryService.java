package ru.cft.shiftlab.contentmaker.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;
import ru.cft.shiftlab.contentmaker.repository.HistoryRepository;
import ru.cft.shiftlab.contentmaker.service.HistoryServiceStories;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryService implements HistoryServiceStories {
    private final HistoryRepository historyRepository;
    private final JsonProcessorService storiesService;

    @Override
    public List<HistoryEntity> getStoryHistory(Long id) {
        return historyRepository.getHistoryByStoryId(id);
    }

    @Override
    public List<HistoryEntity> getAllHistory() {
        return historyRepository.getAllHistory();
    }

    @Override
    public List<HistoryEntity> getBankAndPlatformHistory(String bankId, String platform) {
        return historyRepository.getHistoryByBankAndPlatform(bankId, platform);
    }

    @Override
    public void deleteHistoryByStoryId(Long id) {
        historyRepository.deleteByStoryId(id);
    }

    @Override
    public List<HistoryEntity> getHistoryByBankAndPlatform(String bank, String platform) {
        return historyRepository.getHistoryByBankAndPlatform(bank, platform);
    }



    private void rollBackStories(HistoryEntity history) throws Throwable {
        switch (history.getOperationType()){
            case Create:
                storiesService.deleteService(history.getBankId(), history.getPlatform(), history.getComponentId());
                break;
//            case Delete:
//
//                break;
            case Update:
//                storiesService.deleteService(history.getBankId(), history.getPlatform(), history.getComponentId());
                break;
            case Change:
                storiesService.deleteStoriesFromDb(history.getBankId(), history.getPlatform(), history.getComponentId());

                break;
        }
    }

    private void rollBackFrames(HistoryEntity history){

    }

    private void rollBackBanners(HistoryEntity history){

    }

    public void rollBack(Long idHistory){
        HistoryEntity history = historyRepository.findById(idHistory).orElseThrow(
                () -> new IllegalArgumentException("History not found")
        );
        switch (history.getComponentType()){
            case STORIES:
                rollBackStories(history);
                break;
            case FRAMES:
                rollBackFrames(history);
                break;
            case BANNERS:
                rollBackBanners(history);
                break;
        }

    }

}
