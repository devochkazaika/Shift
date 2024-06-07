package ru.cft.shiftlab.contentmaker.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserDto {
    String password;
    String firstName;
    String lastName;
    String email;
}
