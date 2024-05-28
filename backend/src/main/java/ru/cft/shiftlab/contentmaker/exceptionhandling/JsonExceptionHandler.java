package ru.cft.shiftlab.contentmaker.exceptionhandling;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JsonExceptionHandler extends RuntimeException {
    String message;
    public static JsonExceptionHandler readJsonException(){
        return new JsonExceptionHandler("could not read json file");
    }
}
