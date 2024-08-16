package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shiftlab.contentmaker.entity.History;
import ru.cft.shiftlab.contentmaker.service.HistoryServiceStories;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryServiceStories historyService;

    @GetMapping("/history/stories/get")
    @ResponseStatus(HttpStatus.OK)
    public List<History> getHistoryStory(
            @RequestParam(name = "id")
            Long id
    ) throws IOException {
            return  historyService.getStoryHistory(id);
    }

    @GetMapping("/history/stories/getByBankAndPlatform")
    @ResponseStatus(HttpStatus.OK)
    public List<History> getHistoryStory(
            @RequestParam(name = "bankId")
            String bank,
            @RequestParam(name = "platform")
            String platform
    ) throws IOException {
        return  historyService.getHistoryByBankAndPlatform(bank, platform);
    }

}
