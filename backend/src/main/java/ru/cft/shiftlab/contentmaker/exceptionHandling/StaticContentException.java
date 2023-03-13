package ru.cft.shiftlab.contentmaker.exceptionHandling;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaticContentException extends RuntimeException{
    String message;
    String code;
}
