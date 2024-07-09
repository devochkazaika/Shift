package ru.cft.shiftlab.contentmaker.controller;

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

//    @GetMapping(value = "/get/{id}")
//    @Operation(summary = "Возврат всех банеров с нужным банком.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Банк найден"),
//            @ApiResponse(responseCode = "404", description = "Банк не найден")
//    })
//    @ResponseStatus(HttpStatus.OK)
//    public HttpEntity<MultiValueMap<String, HttpEntity<?>>> getAllBannersByBank(@PathVariable(value = "id") String bankId) throws JsonProcessingException {
//        return bannerProcessorService.getBanners(bankId);
//    }

    @GetMapping(value = "/get")
    public List<Banner> getAllBannersByBank
            (@RequestParam(value = "id")
             String bankId,
             @RequestParam(value = "platform")
             String platform
            ){
        return bannerProcessorService.getBannersList(bankId, platform);
    }

    @DeleteMapping(value = "/delete")
    public void deleteBanner(
            @RequestParam(value = "code")
            String code
    ){
            bannerProcessorService.deleteBanner(code);
    }

    @DeleteMapping(value = "/delete/cascade")
    public void deleteBannerCascade(){

    }
}
