package ru.cft.shiftlab.contentmaker.service;

import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    private final Keycloak keycloak;

    @Autowired
    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void checkKeycloak() {
//         Пример проверки: получение списка реалмов
        System.out.println(keycloak.realm("master").getClientSessionStats());
//        System.out.println("ASDASD");
    }
}