package ru.cft.shiftlab.contentmaker.exceptionhandling;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaticContentException extends RuntimeException{
    String message;
    String code;
}
