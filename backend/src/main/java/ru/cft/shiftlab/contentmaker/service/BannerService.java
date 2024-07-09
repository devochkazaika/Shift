package ru.cft.shiftlab.contentmaker.service;

import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.Banner;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    void setMainBanner(String code, String codeMainBanner);
    void addBanner(String bannerRequestDto,
                   MultipartFile picture,
                   MultipartFile icon) throws IOException;
    List<Banner> getBannersList(String bankId, String platform);
}
