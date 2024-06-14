package ru.cft.shiftlab.contentmaker.service.implementation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.BannerRequestDto;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class BannerProcessorService {

    private final DtoToEntityConverter dtoToEntityConverter;

    public void addBanner(BannerRequestDto bannerRequestDto){
        Banner banner = dtoToEntityConverter.fromBannerDtoToBanner(bannerRequestDto);
        System.out.println(banner);
    }
}
