package org.example.mscourierservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.mscourierservice.exception.CourierNotFound;
import org.example.mscourierservice.exception.ExceptionResponse;
import org.example.mscourierservice.exception.NoAvailableCourierException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CourierNotFound.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handleException(CourierNotFound exception) {
        log.warn("CourierNotFound {}", exception.getMessage());
        return new ExceptionResponse(NOT_FOUND.toString(), exception.getMessage());
    }

    @ExceptionHandler(NoAvailableCourierException.class)
    @ResponseStatus(CONFLICT)
    public ExceptionResponse handleException(NoAvailableCourierException exception) {
        log.warn("NoAvailableCourierException {}", exception.getMessage());
        return new ExceptionResponse(CONFLICT.toString(), exception.getMessage());
    }
}
