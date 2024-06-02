package ru.cft.shiftlab.contentmaker.exceptionhandling;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
public class JsonException extends RuntimeException {
    String message;
    String status;
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static StaticContentException readJsonException(String fileName){
        return new StaticContentException("could not read json file " + fileName, "500");
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static JsonException notFound(String fileName){
        return new JsonException("could not read json file " + fileName, "500");
    }
}
