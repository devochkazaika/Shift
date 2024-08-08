package ru.cft.shiftlab.contentmaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.service.FileSaverService;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.PlatformValid;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.WhiteListValid;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stories/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final FileSaverService storiesService;


    @PostMapping(path = "/approveStory")
    @Operation(summary = "Добавление истории на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "История добавлена на сервер.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void approveStory(
            @RequestParam(value = "bank")
            String bankId,

            @RequestParam(value = "platform")
            String platform,

            @RequestParam(value = "id")
            Long id) throws IOException {
        storiesService.approvedStory(bankId, platform, id);
    }

    @GetMapping("/bank/info/getUnApprovedStories")
    @Operation(summary = "Чтение истории с сервера.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История прочтена с сервера."),
            @ApiResponse(responseCode = "400", description = "Неправильные параметры")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<StoryPresentation> getUnApprovedStories() throws IOException {
        return storiesService.getUnApprovedStories();
    }

    @GetMapping("/bank/info/getDeletedStories")
    @Operation(summary = "Чтение истории с сервера.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История прочтена с сервера."),
            @ApiResponse(responseCode = "400", description = "Неправильные параметры")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<StoryPresentation> getDeletedStories() throws IOException {
        return storiesService.getDeletedStories();
    }

    @DeleteMapping("/bank/info/deletefromDb")
    @Operation(summary = "Удаление истории")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "История успешно удалена"),
            @ApiResponse(responseCode = "404", description = "История не найдена в JSON файле"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера(нет JSON или нет директории с файлами)")
    })
    public ResponseEntity<?> deleteStoryFromDb(
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

        return storiesService.deleteStoriesFromDb(bankId, platform, id);
    }

    @PatchMapping("/bank/info/restore/story")
    public void restoreStory(@Parameter(description = "id истории",
                                        schema = @Schema(type = "string", format = "string"),
                                        example = "0")
                             @RequestParam(name = "id")
                             Long id
    ) throws IOException {
        storiesService.restoreStory(id);
    }
}