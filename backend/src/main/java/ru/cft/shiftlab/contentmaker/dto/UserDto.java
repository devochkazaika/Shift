package ru.cft.shiftlab.contentmaker.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class UserDto {
    String password;
    String firstName;
    String lastName;
    String email;
}
