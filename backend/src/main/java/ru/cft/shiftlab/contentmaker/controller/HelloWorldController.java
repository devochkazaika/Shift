package ru.cft.shiftlab.contentmaker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Тестовый контроллер, чтобы подружить фронт и бэк.
 */
@RestController
public class HelloWorldController {

    /**
     * Данный end-point обрабатывает HTTP GET-запросы.
     *
     * @return строку "Hello, World!".
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld() {
        return "Hello, World!";
    }
}
