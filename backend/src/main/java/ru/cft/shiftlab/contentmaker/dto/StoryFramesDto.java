package ru.cft.shiftlab.contentmaker.dto;

import lombok.Data;

@Data
public class StoryFramesDto {
    private String title; //:"Онлайн конвертация", Подзаголовок в истории
    private String text; //"Обменивайте валюту онлайн по выгодному курсу", Сам текст
    private String textColor;
    private String pictureUrl; //путь до картинки
    private String linkText; //текст гиперссылки
    private String linkUrl; //ссылка на внешний источник
    private Boolean buttonVisible; //нужна ли кнопка true/false
    private String buttonText; //текст кнопки
    private String buttonTextColor; //цвет текста
    private String buttonBackgroundColor; //цвет кнопки
    private String buttonUrl; //ссылка на внутрянку МП, и во внешку ?
}
