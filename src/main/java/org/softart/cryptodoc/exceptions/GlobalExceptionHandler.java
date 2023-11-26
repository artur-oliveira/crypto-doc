package org.softart.cryptodoc.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.softart.cryptodoc.models.internal.GenericError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<GenericError> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericError> handleValidationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message("Request Error").data(ex.getConstraintViolations().stream().map(it -> {
            String fieldName = it.getPropertyPath().toString();
            String errorMessage = it.getMessage();
            return Map.of("field", fieldName, "message", errorMessage);
        })).build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message("Request Error").data(ex.getBindingResult().getAllErrors()).build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
