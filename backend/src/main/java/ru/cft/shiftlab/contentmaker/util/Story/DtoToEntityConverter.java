package ru.cft.shiftlab.contentmaker.util.Story;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.repository.BankRepository;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;

/**
 * Класс, предназначенный для конвертации StoriesRequestDto в StoryPresentation.
 */
@Component
@RequiredArgsConstructor
public class DtoToEntityConverter {

    private final BannerRepository bannerRepository;
    private final BankRepository bankRepository;

    private final ModelMapper modelMapper;

    /**
     * Метод для конвертации StoryDto в StoryPresentation.
     *
     * @param bankId            уникальный идентификатор банка, который отправил запрос.
     * @param storyDto          DTO, которую нужно конвертировать в Entity.
     * @return StoryPresentation, полученный после конвертации StoryDto.
     */
    public StoryPresentation fromStoryDtoToStoryPresentation(String bankId,
                                                             StoryDto storyDto,
                                                             String previewUrl) {
        StoryPresentation storyPresentation = modelMapper.map(storyDto, StoryPresentation.class);
        storyPresentation.setBankId(bankId);

        for(StoryFramesDto storyFramesDto : storyDto.getStoryFramesDtos()) {
            storyPresentation.getStoryPresentationFrames()
                    .add(fromStoryFramesDtoToStoryPresentationFrames(storyFramesDto));
            storyPresentation.setPreviewUrl(previewUrl);
        }

        return storyPresentation;
    }

    /**
     * Метод для конвертации StoryFramesDto в StoryPresentationFrames.
     *
     * @param storyFramesDto DTO, которую нужно конвертировать в Entity.
     * @return StoryPresentationFrames, полученный после конвертации из StoryFramesDto.
     */
    public StoryPresentationFrames fromStoryFramesDtoToStoryPresentationFrames(StoryFramesDto storyFramesDto) {

        return modelMapper.map(storyFramesDto, StoryPresentationFrames.class);
    }

//    public Banner fromBannerDtoToBanner(BannerRequestDto bannerDto) {
//        Banner banner = new Banner();
//        banner.setPlatformType(bannerDto.getPlatform());
//        banner.setPriority(bannerDto.getPriority());
//        banner.setAvailableForAll(bannerDto.getAvailable());
//        Bank bank = bankRepository.findById(bannerDto.getBank()).orElse(null);
//        banner.setBank(bank);
//
//        Banner mainBanner = new Banner();
//        MainBannerDto mainBannerDto = bannerDto.getMainBannerDto();
//        mainBanner.setName(mainBannerDto.getName());
//        mainBanner.setCode(mainBannerDto.getCode());
//        mainBanner.setUrl(mainBannerDto.getUrl());
//        mainBanner.setPlatformType(bannerDto.getPlatform());
//        mainBanner.setPriority(bannerDto.getPriority());
//        mainBanner.setAvailableForAll(bannerDto.getAvailable());
//
//        mainBanner.setBank(bank);
//
//
//
//        mainBanner = bannerRepository.save(mainBanner);
//        banner.setMainBanner(mainBanner);
//
//        OpenBannerDto openBannerDto = bannerDto.getOpenBannerDto();
//        banner.setName(openBannerDto.getName());
//        banner.setCode(openBannerDto.getCode());
//        banner.setText(openBannerDto.getText());
//
//        banner = bannerRepository.save(banner);
//        banner.setMainBanner(mainBanner);
//        return banner;
//    }
    public Banner fromBannerDtoToBanner(BannerDto bannerDto,
                                        String pictureUrl,
                                        String iconUrl){
        Banner banner = modelMapper.map(bannerDto, Banner.class);
        banner.setBank(bankRepository.findBankByName(bannerDto.getBankName()));
        banner.setPicture(pictureUrl);
        banner.setIcon(iconUrl);
        banner = bannerRepository.save(banner);
        return banner;
    }

}
