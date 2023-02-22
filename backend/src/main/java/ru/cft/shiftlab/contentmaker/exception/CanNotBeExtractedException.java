package ru.cft.shiftlab.contentmaker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CanNotBeExtractedException extends RuntimeException {

    public CanNotBeExtractedException(String message) {
        super(message);
    }

}
