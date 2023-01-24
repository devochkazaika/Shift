package ru.cft.shiftlab.contentmaker.exceptionHandling;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoSuchException extends RuntimeException{
    public NoSuchException(String message) {
        super(message);
    }
}
