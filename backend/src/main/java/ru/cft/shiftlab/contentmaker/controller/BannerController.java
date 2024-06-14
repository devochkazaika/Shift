package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shiftlab.contentmaker.dto.BannerRequestDto;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerRepository bannerRepository;
    public void addBanner(
            @RequestParam(value = "banner")
            BannerRequestDto bannerDto){
//
//        bannerRepository.save(bannerDto)
    }
}
