package ru.cft.shiftlab.contentmaker.service.implementation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class BannerProcessorService {

    private final DtoToEntityConverter dtoToEntityConverter;

    public void addBanner(BannerDto bannerDto,
                          MultipartFile picture,
                          MultipartFile icon){

//        String pictureUrl =
//        Banner banner = dtoToEntityConverter.fromBannerDtoToBanner(bannerDto, ,
//                icon);
//        System.out.println(banner);
    }
}
