package ru.cft.shiftlab.contentmaker.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;
import ru.cft.shiftlab.contentmaker.repository.HistoryRepository;
import ru.cft.shiftlab.contentmaker.service.HistoryServiceStories;
import ru.cft.shiftlab.contentmaker.util.keycloak.KeyCloak;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryService implements HistoryServiceStories {
    private final HistoryRepository historyRepository;
    private final JsonProcessorService storiesService;
    private final KeyCloak keyCloak;

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

    @Override
    public List<HistoryEntity> getRequestByUser(){
        return historyRepository.getRequestByUser(keyCloak.getUserName());
    }


    private void rollBackStories(HistoryEntity history) throws Throwable {
        switch (history.getOperationType()){
            case Create:
                try {
                    storiesService.getStory(history.getComponentId());
                    storiesService.deleteStoriesFromDb(history.getBankId(), history.getPlatform(), history.getComponentId());
                    historyRepository.deleteByStoryId(history.getId());
                }
                catch (IllegalArgumentException e){
                    throw e;
                }
                break;
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

    @Override
    public void rollBack(Long idHistory){
        HistoryEntity history = historyRepository.findById(idHistory).orElseThrow(
                () -> new IllegalArgumentException("History not found")
        );
        switch (history.getComponentType()){
            case STORIES:
                try {
                    rollBackStories(history);
                }
                catch (Throwable e){
                    throw new RuntimeException("Could not rollback");
                }
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
