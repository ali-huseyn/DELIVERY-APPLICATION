package org.example.msorderservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.msorderservice.exception.ExceptionResponse;
import org.example.msorderservice.exception.InvalidOrderStatusException;
import org.example.msorderservice.exception.OrderNotFound;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFound.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handleOrderNotFound(OrderNotFound ex) {
        log.warn("Status: {}", ex.getMessage());
        return new ExceptionResponse(NOT_FOUND.toString(), ex.getMessage());
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    @ResponseStatus(CONFLICT)
    public ExceptionResponse handleInvalidOrderStatusException(InvalidOrderStatusException ex) {
        log.warn("Invalid Order status: {}", ex.getMessage());
        return new ExceptionResponse(BAD_REQUEST.toString(), ex.getMessage());
    }

}
