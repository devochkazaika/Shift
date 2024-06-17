package ru.cft.shiftlab.contentmaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.service.implementation.BannerProcessorService;

import java.io.IOException;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerProcessorService bannerProcessorService;
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление банера на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Банер добавлен на сервер.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void addBanner(
            @RequestParam(value = "json")
            String bannerDto,
            @RequestPart(value = "previewImage",required = true)
            MultipartFile picture,
            @RequestPart(value = "cardImages",required = true)
            MultipartFile icon) throws IOException {
        bannerProcessorService.addBanner(bannerDto,
                picture,
                icon);
    }

    @PatchMapping(value = "/setMainBanner")
    @Operation(summary = "Установление главного банера на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MainBanner был установлен")
    })
    @ResponseStatus(HttpStatus.OK)
    public void setMainBanner(
            @RequestParam(value = "code")
            String code,
            @RequestParam(value = "code_mainBanner")
            String codeMainBanner
    ){
        bannerProcessorService.setMainBanner(code, codeMainBanner);
    }
}
