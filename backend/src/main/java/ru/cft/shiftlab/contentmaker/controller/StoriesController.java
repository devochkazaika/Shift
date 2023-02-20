package ru.cft.shiftlab.contentmaker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;

/**
 * Контроллер, обрабатывающий запросы для работы с Story
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/stories")
@Slf4j
@RequiredArgsConstructor
public class StoriesController {
    private final JsonAndImageSaverService storiesService;

    /**
     * Метод, который обрабатывает POST-запрос на сохранение историй.
     *
     * @param storiesRequestDto DTO, которая содержит информацию об историях.
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(@Valid @RequestBody StoriesRequestDto storiesRequestDto) {
        log.info("Received json with StoriesRequestDto, which contains: {} ", storiesRequestDto);

        storiesService.saveFiles(storiesRequestDto);
    }
}
