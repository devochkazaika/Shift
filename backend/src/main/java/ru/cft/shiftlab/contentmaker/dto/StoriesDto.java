package ru.cft.shiftlab.contentmaker.dto;

import lombok.Data;

@Data
public class StoriesDto {
    private String previewTitle; //заголовок историй
    private String previewTitleColor; //цвет текста
    private String previewUrl; //путь до картинки
    private StoryFramesDto storyFramesDto;
}
