package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.exception.IncorrectDateTimeFormat;
import com.aes.dashboard.backend.exception.WrongSortException;
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

    @ExceptionHandler({ WrongSortException.class})
    public ResponseEntity<Object> handleWrongSortException(
            WrongSortException ex, WebRequest request) {
        String message = String.format("Found observations not sorted properly for station %s: o1=%s/%s, o2=%s/%s",
                ex.getO1().getStation().getId(),
                ex.getO1().getId(), ex.getO1().getTime(),
                ex.getO2().getId(), ex.getO2().getTime());
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
    }
}
