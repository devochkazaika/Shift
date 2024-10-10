package ru.cft.shiftlab.contentmaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.service.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.PlatformValid;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.UUIDValid;
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
@ConditionalOnProperty(
        name = "feature-flags.components.stories",
        havingValue = "true"
)
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
    @PostMapping(path = "/add/story", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление истории на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "История добавлена на сервер.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public StoryPresentation addStories(
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
            MultipartFile[] images) throws IOException {

        return storiesService.saveFiles(storiesRequestDto, previewImage, images);
    }

    @PostMapping(path = "/add/frame", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление карточки к истории.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Карточка добавлена на сервер.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public StoryPresentationFrames addFrame(
            @RequestParam(value = "json")
            String frameRequestDto,

            @RequestPart(value = "image",required = true)
            MultipartFile image,

            @RequestParam(value = "bankId")
            @WhiteListValid(message = "bankId must match the allowed")
            @Parameter(description = "Название банка",
                    schema = @Schema(type = "string", format = "string"),
                    example = "tkbbank")
            String bankId,

            @RequestParam(value = "platform")
            @PlatformValid
            @Parameter(description = "Тип платформы",
                    schema = @Schema(type = "string", format = "string"),
                    example = "WEB")
            String platform,

            @RequestParam(value = "id")
            Long id) throws IOException {
        return storiesService.addFrame(frameRequestDto, image, bankId, platform, id);
    }

    /**
     * Get запрос для вывода JSON записей всех историй конкретного банка и платформы
     *
     * @param bankId Имя банка
     * @param platform Тип платформы (ALL PLATFORMS | ANDROID | IOS | WEB)
     * @return list of the JSON Objects
     * @throws IOException
     */
    @GetMapping("/get/all")
    @Operation(summary = "Чтение историй с сервера.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История прочтена с сервера."),
            @ApiResponse(responseCode = "400", description = "Неправильные параметры")
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
    @DeleteMapping("/delete/story")
    @Operation(summary = "Удаление истории")
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
            Long id) throws Throwable {

        return storiesService.deleteService(bankId, platform, id);
    }

    /**
     * Delete запрос для удаления истории
     *
     * @param bankId Имя банка
     * @param platform Тип платформы (ALL PLATFORMS | ANDROID | IOS | WEB)
     * @param storyId Id истории
     * @param frameId UUID карточки истории
     * @throws Throwable
     */
    @DeleteMapping("/delete/frame")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "История успешно удалена"),
            @ApiResponse(responseCode = "404", description = "История не найдена в JSON файле"),
            @ApiResponse(responseCode = "404", description = "Карточка не найдена в истории"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера(нет JSON или нет директории с файлами)")
    })
    @Operation(summary = "Удаление карточки из истории")
    public void deleteFrame(
            @RequestParam
            @WhiteListValid(message = "bankId must match the allowed")
            @Parameter(description = "Название банка",
                    schema = @Schema(type = "string", format = "string"),
                    example = "tkbbank")
            String bankId,

            @RequestParam
            @PlatformValid
            @Parameter(description = "Тип платформы",
                    schema = @Schema(type = "string", format = "string"),
                    example = "ALL PLATFORMS")
            String platform,

            @RequestParam
            @Parameter(description = "Id истории",
                    schema = @Schema(type = "string", format = "string"),
                    example = "0")
            String storyId,

            @RequestParam
            @UUIDValid
            @Parameter(description = "UUID карточки истории",
                    schema = @Schema(type = "string", format = "string"),
                    example = "55151a3b-c9f6-409a-b185-604b2a9afe86")
            String frameId) throws Throwable {
        storiesService.deleteStoryFrame(bankId, platform, storyId, frameId);

    }

    /**
     * Patch запрос для изменения истории
     *
     * @param storiesRequestDto Dto с измененными параметрами истории
     * @param bankId Имя банка
     * @param platform Тип платформы (ALL PLATFORMS | ANDROID | IOS | WEB)
     * @param id Id истории
     * @throws IOException
     */
    @PatchMapping("/change/story")
    @Operation(summary = "Изменение превью истории")
    public void changeStory(
            @RequestParam(value = "json")
            @Parameter(description = "DTO, содержащая информацию об историях, в виде строки JSON.")
            String storiesRequestDto,

            @RequestParam(value = "bankId")
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
            Long id,
            @RequestPart(value = "image",required = false)
            MultipartFile file) throws IOException {
        storiesService.changeStory(storiesRequestDto, file, bankId, platform, id);
    }

    /**
     * Patch запрос для изменения карточки внутри истории
     *
     * @param storiesRequestDto Dto измененной карточки
     * @param bankId Имя банка
     * @param platform Тип платформы (ALL PLATFORMS | ANDROID | IOS | WEB)
     * @param id Id истории
     * @param frameId Id карточки
     * @throws IOException
     */
    @PatchMapping("/change/frame")
    @Operation(summary = "Изменение карточки истории")
    public void changeStoryFrame(@RequestParam(value = "json")
            @Parameter(description = "DTO, содержащая информацию об историях, в виде строки JSON.")
            String storiesRequestDto,

            @RequestParam(value = "bankId")
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
            Long id,

            @Parameter(description = "id истории",
                     schema = @Schema(type = "string", format = "string"),
                     example = "0")
            @RequestParam(name = "frameId")
            @UUIDValid
            String frameId,

            @RequestPart(value = "image",required = false)
            MultipartFile file
            ) throws IOException {
        storiesService.changeFrameStory(storiesRequestDto,
                file,
                bankId,
                platform,
                id,
                frameId);
    }

    /**
     * Запрос для перестановки 2 карточек в истории
     * @param id Id истории
     * @param bankId Имя банка
     * @param platform Тип платформы (ALL PLATFORMS | ANDROID | IOS | WEB)
     * @throws IOException
     */
    @PatchMapping("/change/frame/swap")
    @Operation(summary = "Свап 2 карточек в истори")
    public void swapFrames(
            @RequestParam(name = "id", defaultValue="0")
            Long id,

            @RequestParam(value = "bankId")
            @WhiteListValid
            String bankId,

            @Parameter(description = "Тип платформы",
                    schema = @Schema(type = "string", format = "string"),
                    example = "ALL PLATFORMS")
            @RequestParam(name = "platform", defaultValue="ALL PLATFORMS")
            @PlatformValid
            String platform,

            @RequestParam(name = "newOrder")
            List<String> newOrder
    ) throws IOException {
        storiesService.swapFrames(
                id, bankId, platform, newOrder
        );
    }

    @PatchMapping("/story/rollback")
    public void rollBackChangeRequest(){

    }

    @GetMapping("/get/story")
    @ResponseStatus(HttpStatus.OK)
    public StoryPresentation getStoryById(Long id){
        return storiesService.getStory(id);
    }
}
