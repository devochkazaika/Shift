package ru.cft.shiftlab.contentmaker.util.Story;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.entity.Banner;

/**
 * Класс, предназначенный для конвертации StoriesRequestDto в StoryPresentation.
 */
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {
    private final ModelMapper modelMapper;

    public Banner fromBannerDtoToBanner(BannerDto bannerDto,
                                        String pictureUrl,
                                        String iconUrl){
        Banner banner = modelMapper.map(bannerDto, Banner.class);
        banner.setBankName(bannerDto.getBankName());
        banner.setPicture(pictureUrl);
        banner.setIcon(iconUrl);
        return banner;
    }

}
