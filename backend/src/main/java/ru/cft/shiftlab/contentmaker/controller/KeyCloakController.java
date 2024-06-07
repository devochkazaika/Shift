package ru.cft.shiftlab.contentmaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shiftlab.contentmaker.dto.UserDto;
import ru.cft.shiftlab.contentmaker.service.KeycloakService;

@RestController
@RequiredArgsConstructor
public class KeyCloakController {
    private final KeycloakService service;

    @PostMapping("/createUser")
    private void createUser(@RequestBody UserDto userDto) throws Exception {
        service.createUser(userDto);
    }
}
