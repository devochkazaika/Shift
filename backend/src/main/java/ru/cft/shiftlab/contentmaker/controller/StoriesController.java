package ru.cft.shiftlab.contentmaker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/stories")
@Slf4j
@RequiredArgsConstructor
public class StoriesController {
    private final JsonAndImageSaverService storiesService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(@Valid @RequestBody StoriesRequestDto storiesRequestDto) {
        log.info("Received json with StoriesRequestDto, which contains: {} ", storiesRequestDto);

        storiesService.saveFiles(storiesRequestDto);
    }
}
