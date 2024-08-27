package ru.cft.shiftlab.contentmaker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.Banner;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    /**
     * Сохранение банера в БД
     * @param bannerRequestDto
     * @param picture
     * @param icon
     * @throws IOException
     */
    void addBanner(String bannerRequestDto,
                   MultipartFile picture,
                   MultipartFile icon) throws IOException;

    /**
     * Установка банеру MainBanner
     * @param code
     * @param codeMainBanner
     */
    void setMainBanner(String code, String codeMainBanner);

    /**
     * Возврат всех банеров банка + платформы
     * @param bankId
     * @param platform
     * @return
     */
    List<Banner> getBannersList(String bankId, String platform);

    /**
     * Удаление банера
     * @param code
     */
    void deleteBanner(String code);

    /**
     * Удаление банера вместе с его MainBanner
     * @param code
     */
    void deleteBannerCascade(String code);

    /**
     * Изменение параметров банера
     * @param bannerDto
     * @param code
     * @throws JsonProcessingException
     */
    void patchBanner(String bannerDto, String code) throws JsonProcessingException;
}
