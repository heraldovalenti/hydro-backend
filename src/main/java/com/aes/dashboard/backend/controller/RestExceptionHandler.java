package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.exception.IncorrectDateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ EntityNotFound.class})
    public ResponseEntity<Object> handleNotFound(
            EntityNotFound ex, WebRequest request) {
        String message = String.format("%s with ID %s was not found",
                ex.getEntity().getName(), ex.getId());
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ IncorrectDateTimeFormat.class})
    public ResponseEntity<Object> handleDateTimeFormatException(
            IncorrectDateTimeFormat ex, WebRequest request) {
        String message = String.format("Could not parse %s (index %s), required format is %s",
                ex.getInput(), ex.getIndex(), ex.getFormat());
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
