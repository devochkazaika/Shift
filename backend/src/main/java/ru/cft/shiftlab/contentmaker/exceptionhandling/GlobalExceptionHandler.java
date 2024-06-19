package ru.cft.shiftlab.contentmaker.exceptionhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> IllegalArgumentHandler(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> IllegalArgumentHandler(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JsonException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> JsonHandler(JsonException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationException handleValidationException(ConstraintViolationException ex) {
        return new ValidationException(ex.getMessage(), "404");
    }
    @ExceptionHandler(StaticContentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StaticContentException handleStaticContentException(StaticContentException staticContentException) {
        return staticContentException;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, Set<String>> errorsMap =  fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );

        return new ResponseEntity<>(errorsMap.isEmpty()? ex:errorsMap, headers, status);
    }
}
