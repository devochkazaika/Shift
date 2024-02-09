package ru.cft.shiftlab.contentmaker.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Контроллер, обрабатывающий запросы для работы с Story.
 */
//@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoriesController {
    private final JsonProcessorService storiesService;

    /**
     * Метод, который обрабатывает POST-запрос на сохранение историй.
     *
     * @param storiesRequestDto DTO, которая содержит информацию об историях.
     */
    @PostMapping("/addOld")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(@Valid @RequestBody StoriesRequestDto storiesRequestDto) {

        storiesService.saveFiles(storiesRequestDto, null, false);
    }

    /**
     * NEW Метод, который обрабатывает POST-запрос на сохранение историй.
     * Основан на js формате FormData
     *
     * @param storiesRequestDto DTO, которая содержит информацию об историях.
     * @param images файлы с картинками.
     */
    @PostMapping("/add")
//    @JsonIgnoreProperties(ignoreUnknown = true)
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(@Valid @RequestBody StoriesRequestDto storiesRequestDto,
                           @RequestParam("images") MultipartFile[] images) {

        storiesService.saveFiles(storiesRequestDto, images, false);
    }

    @GetMapping("/{bankId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, List<StoryPresentation>> getStories(
            @PathVariable String bankId,
            @RequestParam(defaultValue="ALL PLATFORMS") String platform) {

        return storiesService.getFilePlatform(bankId, platform);
//        String FileIos = fileNameCreator.createFileName(bankId, "IOS");
//        String FileAndroid = fileNameCreator.createFileName(bankId, "ANDROID");


    }
}
