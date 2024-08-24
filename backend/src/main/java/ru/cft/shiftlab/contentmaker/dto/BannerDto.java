package ru.cft.shiftlab.contentmaker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {
    String bankName;
    String code;
    String name;
    String platformType;
    Integer priority;
    String url;
    String textUrl;
    String siteSection;
    String color;
    String text;
    Boolean availableForAll;
}
