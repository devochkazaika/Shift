package ru.cft.shiftlab.contentmaker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "VW_RPT_BANNER_MAIN")
public class MainBanner {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

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
     * Внутренние баннеры.
     */
//    @OneToMany(mappedBy = "mainBanner", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy("C_PRIORITY")
//    private List<Banner> banners;

    /**
     * Размер коллекции Внутренние баннеры для более оптимальных SQL запросов.
     */
    @Column(name = "C_DEPENDENT_COUNT")
    private Integer bannersSize;

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
     * Признак доступен всем.
     */
    @Column(name = "C_AVAILABLE_TO_ALL")
    private boolean availableForAll;

    /**
     * Тип платформы для отображения Баннера.
     */
    @Column(name = "C_PLATFORM")
    private String platformType;

}