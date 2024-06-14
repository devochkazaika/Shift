package ru.cft.shiftlab.contentmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerRequestDto {
    String platform;
    Integer priority;
    Boolean available;
    String siteSection;
    @JsonProperty("mainBanner")
    MainBannerDto mainBannerDto;
    @JsonProperty("openBanner")
    OpenBannerDto openBannerDto;
}
