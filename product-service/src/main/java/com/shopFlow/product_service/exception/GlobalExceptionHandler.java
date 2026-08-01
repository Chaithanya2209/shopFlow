package com.shopFlow.product_service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                productNotFoundException.getMessage(),
                LocalDateTime.now());

        return  ResponseEntity.status( HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentsNotValidException(MethodArgumentNotValidException ex) {


        String message  = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
