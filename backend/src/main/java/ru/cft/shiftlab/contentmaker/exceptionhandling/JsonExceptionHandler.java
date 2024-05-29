package ru.cft.shiftlab.contentmaker.exceptionhandling;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
public class JsonExceptionHandler extends RuntimeException {
    String message;
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static StaticContentException readJsonException(String fileName){
        return new StaticContentException("could not read json file " + fileName, "500");
    }
}
