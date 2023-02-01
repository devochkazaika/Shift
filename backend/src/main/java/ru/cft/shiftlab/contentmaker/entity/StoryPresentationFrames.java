package ru.cft.shiftlab.contentmaker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
