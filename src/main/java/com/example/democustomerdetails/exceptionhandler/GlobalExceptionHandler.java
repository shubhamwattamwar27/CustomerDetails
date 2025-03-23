package com.example.democustomerdetails.exceptionhandler;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationExceptions(ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations()
                .forEach(violation -> {
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage());
                });
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpclientErrorExceptions(HttpClientErrorException ex) {
        return new ResponseEntity<>(ex.getStatusText(), BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleHttpServerErrorExceptions(HttpServerErrorException ex) {
        return new ResponseEntity<>(ex.getStatusText(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnyExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

}
