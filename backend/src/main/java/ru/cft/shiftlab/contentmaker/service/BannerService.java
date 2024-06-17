package ru.cft.shiftlab.contentmaker.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BannerService {
    void setMainBanner(String code, String codeMainBanner);
    void addBanner(String bannerRequestDto,
                   MultipartFile picture,
                   MultipartFile icon) throws IOException;
}
