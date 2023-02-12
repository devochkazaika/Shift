package ru.cft.shiftlab.contentmaker.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoryPresentationFrames {

    private String title;
    private String text;
    private String textColor;
    private byte[] pictureUrl;
    private String visibleLinkOrButtonOrNone;
    private String linkText;
    private String linkUrl;
    private String buttonText;
    private String buttonTextColor;
    private String buttonBackgroundColor;
    private String buttonUrl;
    private String gradient;


}
