package ru.cft.shiftlab.contentmaker.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaticContentException extends RuntimeException{
    String message;
    String code;
}
