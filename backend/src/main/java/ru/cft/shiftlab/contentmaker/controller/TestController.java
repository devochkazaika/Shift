package ru.cft.shiftlab.contentmaker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shiftlab.contentmaker.service.KeycloakService;

@RestController
public class TestController {

    private final KeycloakService keycloakService;

    @Autowired
    public TestController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @GetMapping("/check-keycloak")
    public String checkKeycloak() {
        keycloakService.checkKeycloak();
        return "Keycloak bean is working!";
    }
}