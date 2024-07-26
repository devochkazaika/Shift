package ru.cft.shiftlab.contentmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
@Entity
@Table(name = "stories")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoryPresentation {

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

    @Column(name = "platform")
    @JsonIgnore
    private String platform;

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

    @JsonIgnore
    @Column(name = "approved")
    private Boolean approved;

    /**
     * Список карточек истории.
     */
    @JsonProperty("storyFrames")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "story")
    private List<StoryPresentationFrames> storyPresentationFrames = new ArrayList<>();

}
