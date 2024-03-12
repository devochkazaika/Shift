package ru.cft.shiftlab.contentmaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Контроллер, обрабатывающий запросы для работы с Story.
 */
@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoriesController {
    private final JsonProcessorService storiesService;

    /**
     * Метод, который обрабатывает POST-запрос на сохранение историй.
     * Основан на js формате FormData
     *
     * @param storiesRequestDto DTO, которая содержит информацию об историях.
     * @param images файлы с картинками.
     */
    @PostMapping("/add")
    @Operation(summary = "Добавление истории на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История добавлена на сервер.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(@RequestPart(value = "json") String storiesRequestDto,
                           @RequestParam(value = "previewImage",required = false) MultipartFile previewImage,
                           @RequestParam(value = "cardImages",required = false) MultipartFile[] images) {

        storiesService.saveFiles(storiesRequestDto, previewImage, images, false);
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
