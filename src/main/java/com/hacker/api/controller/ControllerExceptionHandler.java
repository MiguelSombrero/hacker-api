package com.hacker.api.controller;

import com.hacker.api.parsers.SpreadsheetParserTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    protected static Logger logger = LoggerFactory.getLogger(SpreadsheetParserTemplate.class);

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String body = "Illegal argument in request";

        logger.info(ex.getLocalizedMessage());

        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { IndexOutOfBoundsException.class })
    protected ResponseEntity<Object> handleIndexOutOfBounds(RuntimeException ex, WebRequest request) {
        String body = "Index out of bounds";

        logger.info(ex.getLocalizedMessage());

        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}