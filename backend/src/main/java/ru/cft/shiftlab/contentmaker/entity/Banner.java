package ru.cft.shiftlab.contentmaker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность баннера, которая будет отображаться в БД.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "VW_RPT_BANNER")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Код баннера.
     */
    @Column(name = "C_CODE")
    private String code;

    /**
     * Название.
     */
    @Column(name = "C_NAME")
    private String name;

    /**
     * Название картинки.
     */
    @Column(name = "C_PICTURE")
    private String picture;

    /**
     * Банк владельца баннера.
     */
    @ManyToOne
    @JoinColumn(name = "C_BANK")
    private Bank bank;

    /**
     * Основной баннер.
     */
    @ManyToOne
    @JoinColumn(name = "C_MAIN_BANNER")
    private Banner mainBanner;

    /**
     * Название иконки.
     */
    @Column(name = "C_ICON")
    private String icon;

    /**
     * Текст баннера.
     */
    @Column(name = "C_TEXT")
    private String text;

    /**
     * Ссылка для перехода при клике на баннер.
     */
    @Column(name = "C_URL")
    private String url;

    /**
     * Текст ссылки.
     */
    @Column(name = "C_URL_TEXT")
    private String urlText;

    /**
     * Приоритет отображения баннера.
     */
    @Column(name = "C_PRIORITY")
    private Integer priority;

    /**
     * Код цвета кнопки.
     */
    @Column(name = "C_COLOR")
    private String color;

    /**
     * Признак Доступен всем.
     */
    @Column(name = "C_AVAILABLE_TO_ALL")
    private boolean availableForAll;

    /**
     * Тип платформы для отображения Банера.
     */
    @Column(name = "C_PLATFORM")
    private String platformType;
}
