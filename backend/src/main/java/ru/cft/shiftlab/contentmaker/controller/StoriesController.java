package ru.cft.shiftlab.contentmaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;

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
     * Основан на формате FormData
     *
     * @param storiesRequestDto DTO, которая содержит информацию об историях, в виде строки json.
     * @param images файлы с картинкой превью.
     * @param previewImage файлы с картинками карточек.
     */
    @PostMapping("/add")
    @Operation(summary = "Добавление истории на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "История добавлена на сервер.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(@RequestParam(value = "json") String storiesRequestDto,
                           @RequestParam(value = "previewImage",required = false) MultipartFile previewImage,
                           @RequestParam(value = "cardImages",required = false) MultipartFile[] images) {

        storiesService.saveFiles(storiesRequestDto, previewImage, images);
    }


    /**
     * Метод, который обрабатывает GET-запрос на чтение историй.
     * Основан на формате FormData
     *
     * @param bankId название банка.
     * @param platform платформа, для которой создана история.
     */
    @GetMapping("/bank/info")
    @Operation(summary = "Чтение истории с сервера.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "История прочтена с сервера.")
    })
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public HttpEntity<MultiValueMap<String, HttpEntity<?>>> getStories(
            @RequestParam(name = "bankId") String bankId,
            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS") String platform) {

        return storiesService.getFilePlatform(bankId, platform);
    }

    @DeleteMapping("/bank/info/delete")
    public void deleteStories(
            @RequestParam(name = "bankId") String bankId,
            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS") String platform,
            @RequestParam(name = "id") String id) throws Exception {

        storiesService.deleteService(bankId, platform, id);
    }
}
