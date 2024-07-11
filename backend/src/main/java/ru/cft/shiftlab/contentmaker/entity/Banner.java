package ru.cft.shiftlab.contentmaker.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Сущность баннера, которая будет отображаться в БД.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@ToString
@Table(name = "test_banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Код баннера.
     */
    @Column(name = "code")
    private String code;

    /**
     * Название.
     */
    @Column(name = "name")
    @Length(max = 100)
    private String name;

    /**
     * Название картинки.
     */
    @Column(name = "picture")
    private String picture;

    /**
     * Банк владельца баннера.
     */
    @ManyToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    /**
     * Основной баннер.
     */
    @ManyToOne
    @JoinColumn(name = "main_banner")
    private Banner mainBanner;

    /**
     * Название иконки.
     */
    @Column(name = "icon")
    private String icon;

    /**
     * Текст баннера.
     */
    @Column(name = "text")
    @Length(max = 1900)
    private String text;

    /**
     * Ссылка для перехода при клике на баннер.
     */
    @Column(name = "url")
    private String url;

    /**
     * Текст ссылки.
     */
    @Column(name = "url_text")
    private String textUrl;

    /**
     * Приоритет отображения баннера.
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * Код цвета кнопки.
     */
    @Column(name = "color")
    private String color;

    /**
     * Признак Доступен всем.
     */
    @Column(name = "available")
    private Boolean availableForAll;

    /**
     * Тип платформы для отображения Банера.
     */
    @Column(name = "platform")
    private String platformType;
}
