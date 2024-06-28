package ru.cft.shiftlab.contentmaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.service.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.PlatformValid;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.WhiteListValid;

import java.io.IOException;
import java.util.List;

/**
 * Контроллер, обрабатывающий запросы для работы с Story.
 */
@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
@Validated
public class StoriesController {
    private final FileSaverService storiesService;

    /**
     * Метод, который обрабатывает POST-запрос на сохранение историй.
     * Основан на формате FormData
     *
     * @param storiesRequestDto DTO, которая содержит информацию об историях, в виде строки json.
     * @param images файлы с картинкой превью.
     * @param previewImage главная картинка.
     */
    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление истории на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "История добавлена на сервер.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void addStories(
            @RequestParam(value = "json")
            @Parameter(description = "DTO, содержащая информацию об историях, в виде строки JSON.")
            String storiesRequestDto,

            @RequestPart(value = "previewImage",required = true)
            @Parameter(description = "Главная картинка.",
                    schema = @Schema(type = "string", format = "binary"),
                    content = @Content(mediaType = "multipart/form-data"))
            MultipartFile previewImage,

            @RequestPart(value = "cardImages",required = false)
            @Parameter(description = "Файлы с картинками карточек.",
                    schema = @Schema(type = "array", format = "binary"),
                    content = @Content(mediaType = "multipart/form-data"))
            MultipartFile[] images) {

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
            @RequestParam(name = "bankId")
            @Parameter(description = "Название банка",
                    schema = @Schema(type = "string", format = "string"),
                    example = "tkkbank")
            @WhiteListValid(message = "bankId must match the allowed")
            String bankId,

            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS")
            @Parameter(description = "Тип платформы",
                    schema = @Schema(type = "string", format = "string"),
                    example = "WEB")
            @PlatformValid
            String platform) throws IOException {

        return storiesService.getFilePlatform(bankId, platform);
    }

    @GetMapping("/bank/info/getJson")
    @Operation(summary = "Чтение истории с сервера.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "История прочтена с сервера.")
    })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public HttpEntity<List<StoryPresentation>> getStoriesJson(
            @RequestParam(name = "bankId")
            @Parameter(description = "Название банка",
                    schema = @Schema(type = "string", format = "string"),
                    example = "tkkbank")
            @WhiteListValid(message = "bankId must match the allowed")
            String bankId,

            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS")
            @Parameter(description = "Тип платформы",
                    schema = @Schema(type = "string", format = "string"),
                    example = "WEB")
            @PlatformValid
            String platform) throws IOException {

        return storiesService.getFilePlatformJson(bankId, platform);
    }
    /**
     * Метод, который обрабатывает DELETE-запрос на удаление историй.
     *
     * @param bankId название банка.
     * @param platform платформа, для которой создана история.
     * @param id id истории
     */
    @DeleteMapping("/bank/info/delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "История успешно удалена"),
            @ApiResponse(responseCode = "404", description = "История не найдена в JSON файле"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера(нет JSON или нет директории с файлами)")
    })
    public ResponseEntity<?> deleteStories(
            @RequestParam(name = "bankId")
            @Parameter(description = "Название банка",
                    schema = @Schema(type = "string", format = "string"),
                    example = "tkbbank")
            @WhiteListValid(message = "bankId must match the allowed")
            String bankId,

            @Parameter(description = "Тип платформы",
                    schema = @Schema(type = "string", format = "string"),
                    example = "ALL PLATFORMS")
            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS")
            @PlatformValid
            String platform,

            @Parameter(description = "id истории",
                    schema = @Schema(type = "string", format = "string"),
                    example = "0")
            @RequestParam(name = "id")
            String id) throws Throwable {

        return storiesService.deleteService(bankId, platform, id);
    }

    /**
     * Запрос, предназначенный для удаления истории
     * @param bankId
     * @param platform
     * @param storyId
     * @param frameId
     * @throws Throwable
     */
    @DeleteMapping("/bank/info/delete/frame/")
    public void deleteFrame(
            @RequestParam
            @WhiteListValid(message = "bankId must match the allowed")
            String bankId,

            @RequestParam
            @PlatformValid
            String platform,

            @RequestParam String storyId,
            @RequestParam String frameId) throws Throwable {
        storiesService.deleteStoryFrame(bankId, platform, storyId, frameId);

    }

    /**
     * Patch-Запрос, предназначенный для изменения истории
     * @param storiesRequestDto
     * @param bankId
     * @param platform
     * @param id
     * @throws IOException
     */
    @PatchMapping("/bank/info/change")
    public void changeStory(@RequestParam(value = "json")
                            @Parameter(description = "DTO, содержащая информацию об историях, в виде строки JSON.")
                            String storiesRequestDto,

                            @RequestParam(value = "bankId")
                            String bankId,

                            @Parameter(description = "Тип платформы",
                                    schema = @Schema(type = "string", format = "string"),
                                    example = "ALL PLATFORMS")
                            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS")
                            String platform,

                            @Parameter(description = "id истории",
                                    schema = @Schema(type = "string", format = "string"),
                                    example = "0")
                            @RequestParam(name = "id")
                            Long id) throws IOException {
        storiesService.changeStory(storiesRequestDto, bankId, platform, id);
    }

    /**
     * Patch-Запрос, предназначенный для изменения карточки внутри истории
     * @param storiesRequestDto
     * @param bankId
     * @param platform
     * @param id
     * @param frameId
     * @throws IOException
     */
    @PatchMapping("/bank/info/change/frame")
    public void changeStoryFrame(@RequestParam(value = "json")
                            @Parameter(description = "DTO, содержащая информацию об историях, в виде строки JSON.")
                            String storiesRequestDto,

                            @RequestParam(value = "bankId")
                            String bankId,

                            @Parameter(description = "Тип платформы",
                                    schema = @Schema(type = "string", format = "string"),
                                    example = "ALL PLATFORMS")
                            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS")
                            String platform,

                            @Parameter(description = "id истории",
                                    schema = @Schema(type = "string", format = "string"),
                                    example = "0")
                            @RequestParam(name = "id")
                            Long id,

                            @Parameter(description = "id истории",
                                     schema = @Schema(type = "string", format = "string"),
                                     example = "0")
                            @RequestParam(name = "frameId")
                            Integer frameId) throws IOException {
        storiesService.changeFrameStory(storiesRequestDto,
                bankId,
                platform,
                id,
                frameId);
    }
}
