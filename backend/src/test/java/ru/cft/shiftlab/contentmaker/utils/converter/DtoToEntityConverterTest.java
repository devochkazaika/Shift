package ru.cft.shiftlab.contentmaker.utils.converter;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.cft.shiftlab.contentmaker.dto.BannerRequestDto;
import ru.cft.shiftlab.contentmaker.dto.MainBannerDto;
import ru.cft.shiftlab.contentmaker.dto.OpenBannerDto;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class DtoToEntityConverterTest {

    @Mock
    private BannerRepository bannerRepository;

    @InjectMocks
    private DtoToEntityConverter dtoToEntityConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dtoToEntityConverter = new DtoToEntityConverter(bannerRepository, new ModelMapper());
    }

    @Test
    public void fromOpenBannerDtoToBanner() {
        // Arrange
        OpenBannerDto openBannerDto = OpenBannerDto.builder()
                .code("<h1>Test code</h1>")
                .name("Test banner")
                .text("test text")
                .build();

        MainBannerDto mainBannerDto = MainBannerDto.builder()
                .name("test name mainbanner")
                .code("<h1>Test code mainbanner</h1>")
                .url("test url")
                .build();

        BannerRequestDto bannerRequestDto = BannerRequestDto.builder()
                .platform("WEB")
                .priority(1)
                .available(true)
                .siteSection("SADASD")
                .mainBannerDto(mainBannerDto)
                .openBannerDto(openBannerDto)
                .build();

        Banner mainBanner = new Banner();
        mainBanner.setName(mainBannerDto.getName());
        mainBanner.setCode(mainBannerDto.getCode());
        mainBanner.setUrl(mainBannerDto.getUrl());

        Banner banner = new Banner();
        banner.setPlatformType(bannerRequestDto.getPlatform());
        banner.setPriority(bannerRequestDto.getPriority());
        banner.setAvailableForAll(bannerRequestDto.getAvailable());
        banner.setMainBanner(mainBanner);

        when(bannerRepository.save(any(Banner.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Banner result = dtoToEntityConverter.fromBannerDtoToBanner(bannerRequestDto);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getMainBanner());
        assertEquals(mainBannerDto.getName(), result.getMainBanner().getName());
        assertEquals(mainBannerDto.getCode(), result.getMainBanner().getCode());
        assertEquals(mainBannerDto.getUrl(), result.getMainBanner().getUrl());
        assertEquals(openBannerDto.getName(), result.getName());
        assertEquals(openBannerDto.getCode(), result.getCode());
        assertEquals(openBannerDto.getText(), result.getText());
        System.out.println(banner);
    }
}
