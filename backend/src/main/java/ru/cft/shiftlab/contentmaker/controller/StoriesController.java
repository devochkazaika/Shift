package ru.cft.shiftlab.contentmaker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;

import java.io.IOException;

@RestController
@RequestMapping("/stories")
@Slf4j
@RequiredArgsConstructor
public class StoriesController {

    private final JsonAndImageSaverService storiesService;

    @PostMapping("/add")
    public void addStories (@Valid @RequestBody StoriesRequestDto storiesRequestDto) {

        log.info("Received json with StoriesRequestDto, which contains: {} ", storiesRequestDto);

        storiesService.saveFiles(storiesRequestDto);
    }
}
