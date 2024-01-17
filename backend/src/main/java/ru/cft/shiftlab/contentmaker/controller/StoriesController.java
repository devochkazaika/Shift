package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonAndImageSaverService;
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;

import javax.validation.Valid;

/**
 * Контроллер, обрабатывающий запросы для работы с Story.
 */
//@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoriesController {
    private final JsonAndImageSaverService storiesService;

    private final FileNameCreator fileNameCreator;

    /**
     * Метод, который обрабатывает POST-запрос на сохранение историй.
     *
     * @param storiesRequestDto DTO, которая содержит информацию об историях.
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(@Valid @RequestBody StoriesRequestDto storiesRequestDto) {

        storiesService.saveFiles(storiesRequestDto, false);
    }

    @GetMapping("/{bankId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void getStories(@PathVariable String bankId) {
        String FileIos = fileNameCreator.createFileName(bankId, "IOS");
        String FileAndroid = fileNameCreator.createFileName(bankId, "ANDROID");


    }

}
