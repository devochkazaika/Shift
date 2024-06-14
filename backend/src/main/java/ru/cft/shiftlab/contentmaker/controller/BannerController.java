package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlab.contentmaker.dto.BannerRequestDto;
import ru.cft.shiftlab.contentmaker.service.implementation.BannerProcessorService;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerProcessorService bannerProcessorService;
    @PostMapping(value = "/add")
    @ResponseBody
    public void addBanner(
            @RequestBody
            BannerRequestDto bannerDto){
        bannerProcessorService.addBanner(bannerDto);
//
//        bannerRepository.save(bannerDto)
    }
}
