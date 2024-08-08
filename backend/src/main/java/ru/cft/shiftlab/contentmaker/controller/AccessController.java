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

@RestController()
@RequestMapping("/access")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessController {
    @Value("${feature-flags.components.banners}")
    Boolean banners;

    @AllArgsConstructor
    @Getter
    @Setter
    public class Flags{
        Boolean banners;
    }

    @GetMapping("/get")
    public Flags getFlags(){
        return new Flags(banners);
    }
}
