package ru.cft.shiftlab.contentmaker.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpenBannerDto {
    String name;
    String code;
    String text;
}