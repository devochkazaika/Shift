package ru.cft.shiftlab.contentmaker.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MainBannerDto {
    String name;
    String code;
    String url;
}
