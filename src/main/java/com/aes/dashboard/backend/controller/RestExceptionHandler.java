package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.exception.StationNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ StationNotFound.class })
    public ResponseEntity<Object> handleNotFound(
            StationNotFound ex, WebRequest request) {
        return handleExceptionInternal(ex, "Station ID not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
