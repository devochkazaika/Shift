package ru.cft.shiftlab.contentmaker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.service.implementation.BannerProcessorService;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.PlatformValid;
import ru.cft.shiftlab.contentmaker.util.validation.annotation.WhiteListValid;

import java.io.IOException;
import java.util.List;

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

    @GetMapping(value = "/get")
    @Operation(summary = "Возвращение всех банеров.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MainBanner был установлен")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<Banner> getAllBannersByBank(
            @RequestParam(value = "id")
            @WhiteListValid
            String bankId,

            @RequestParam(value = "platform")
            @PlatformValid
            String platform
            ){
        return bannerProcessorService.getBannersList(bankId, platform);
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "Удаление банера")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Банер успешно удален")
    })
    @ResponseStatus(HttpStatus.OK)
    public void deleteBanner(
            @RequestParam(value = "code")
            String code
    ){
            bannerProcessorService.deleteBanner(code);
    }

    @DeleteMapping(value = "/delete/cascade")
    @Operation(summary = "Удаление банера")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Банер успешно удален вместе с mainBanner")
    })
    @ResponseStatus(HttpStatus.OK)
    public void deleteBannerCascade(
            @RequestParam(value = "code")
            String code
    ){
        bannerProcessorService.deleteBannerCascade(code);
    }

    @PatchMapping(value = "/change/banner")
    @Operation(summary = "Изменение банера")
    @ResponseStatus(HttpStatus.OK)
    public void changeBanner(@RequestParam(value = "json")
                             String bannerDto,
                             @RequestParam(value = "code")
                             String code) throws JsonProcessingException {
        bannerProcessorService.patchBanner(bannerDto, code);
    }
}
