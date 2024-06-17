package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.service.implementation.BannerProcessorService;

import java.io.IOException;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerProcessorService bannerProcessorService;
    @PostMapping(value = "/add")
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

    @PostMapping(value = "/setMainBanner")
    public void setMainBanner(
            @RequestParam(value = "code")
            String code,
            @RequestParam(value = "code_mainBanner")
            String codeMainBanner
    ){
        bannerProcessorService.setMainBanner(code, codeMainBanner);
    }
}
