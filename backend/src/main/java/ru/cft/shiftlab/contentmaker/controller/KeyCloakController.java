package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlab.contentmaker.dto.UserDto;
import ru.cft.shiftlab.contentmaker.service.implementation.KeycloakServiceImpl;

@RestController
@RequiredArgsConstructor
public class KeyCloakController {
    private final KeycloakServiceImpl service;

    @PostMapping("/createUser")
    private void createUser(@RequestBody UserDto userDto) throws Exception {
        service.createUser(userDto);
    }
    @DeleteMapping("/createUser")
    private void delete(@RequestBody String username) throws Exception {
        service.deleteUser(username);
    }
    /**
     * Для теста кейклока
     * @return ошибки нет, тогда работает
     */
    @GetMapping("/check-keycloak")
    public String checkKeycloak() {
        service.getRealm().getClientSessionStats();
        return "Keycloak bean is working!";
    }
}
