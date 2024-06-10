package ru.cft.shiftlab.contentmaker.service;

import ru.cft.shiftlab.contentmaker.dto.UserDto;

public interface KeycloakService {
    void createUser(UserDto user) throws Exception;
    void deleteUser(String userName);
}
