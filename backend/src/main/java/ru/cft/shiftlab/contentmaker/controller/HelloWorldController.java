package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Тестовый контроллер, чтобы подружить фронт и бэк.
 */
@RestController
@RequiredArgsConstructor
public class HelloWorldController {

    private final Keycloak keycloak;

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

    /**
     * Для теста кейклока
     * @return ошибки нет, тогда работает
     */
    @GetMapping("/check-keycloak")
    public String checkKeycloak() {
        System.out.println(keycloak.realm("master").getClientSessionStats());
        return "Keycloak bean is working!";
    }
}
