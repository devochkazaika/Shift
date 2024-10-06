package ru.cft.shiftlab.contentmaker.entity.stories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Сущность, которая содержит информацию об итоговом JSON-файле.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@With
@Table(name = "stories")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoryPresentation  {
    public enum Status {APPROVED, NOTAPPROVED, DELETED, CHANGED};

    /**
     * Идентификатор истории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Код банка.
     */
    @Column(name = "bank_id")
    private String bankId;

    /**
     * Платформа (ANDROID | WEB | IOS | ALL PLATFORMS)
     */
    @Column(name = "platform")
    @Builder.Default
    private String platform = null;

    /**
     * Размер шрифта.
     */
    @Column(name = "font_size")
    @JsonProperty("font-size")
    private String fontSize;

    /**
     * Заголовок превью.
     */
    @Column(name = "preview_title")
    private String previewTitle;

    /**
     * Цвет заголовка превью.
     */
    @Column(name="preview_title_color")
    private String previewTitleColor;

    /**
     * Путь до картинки для превью.
     */
    @Column(name = "preview_url")
    private String previewUrl;

    /**
     * Градиент превью.
     */
    @Column(name = "preview_gradient")
    private String previewGradient;

    /**
     * Статус истории (APPROVED | NOTAPPROVED | DELETED)
     */
    @JsonIgnore
    @Column(name = "approved")
    @Enumerated(EnumType.STRING)
    private Status approved;

    /**
     * Список карточек истории.
     */
    @JsonProperty("storyFrames")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="story")
    @Builder.Default
    private List<StoryPresentationFrames> storyPresentationFrames = new ArrayList<>();

    /**
     * Список запросов на изменение
     */
    @JoinColumn(name = "id_main_story")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoryPresentation> storyPresentationChanged;
}
