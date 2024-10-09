package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;
import ru.cft.shiftlab.contentmaker.service.HistoryServiceStories;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryServiceStories historyService;

    @GetMapping("/history/stories/get")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoryEntity> getHistoryStory(
            @RequestParam(name = "id")
            Long id
    ) throws IOException {
            return  historyService.getStoryHistory(id);
    }

    @GetMapping("/history/stories/getByBankAndPlatform")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoryEntity> getHistoryStory(
            @RequestParam(name = "bankId")
            String bank,
            @RequestParam(name = "platform")
            String platform
    ) throws IOException {
        return historyService.getHistoryByBankAndPlatform(bank, platform);
    }

    @GetMapping("/history/stories/getAllHistory")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoryEntity> getAllHistory(
    ) throws IOException {
        return historyService.getAllHistory();
    }

    @PatchMapping("/history/rollback")
    public boolean rollbackHistory(@RequestParam(name = "id") Long historyId){
        historyService.rollBack(historyId);
        return true;
    }

}
