package ru.cft.shiftlab.contentmaker.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллера для возврата флагов доступа для фронта
 */
@RestController
@RequestMapping("/access")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessController {
    /**
     * Доступность банеров
     */
    @Value("${feature-flags.components.banners}")
    Boolean banners;

    /**
     * Доступность историй
     */
    @Value("${feature-flags.components.stories}")
    Boolean stories;

    /**
     * Доступность админки
     */
    @Value("${feature-flags.components.admin_panel}")
    Boolean adminPanel;

    @Value("${feature-flags.components.show_story}")
    Boolean show_story;

    @AllArgsConstructor
    @Getter
    @Setter
    public class Flags{
        Boolean banners;
        Boolean stories;
        Boolean admin_panel;
        Boolean show_story;
    }

    @GetMapping("/get")
    public Flags getFlags(){
        return new Flags(banners, stories, adminPanel, show_story);
    }
}
