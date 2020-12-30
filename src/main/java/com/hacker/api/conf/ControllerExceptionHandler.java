package com.hacker.api.conf;

import com.hacker.api.parsers.SheetToBooksParserTemplate;
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
    protected static Logger logger = LoggerFactory.getLogger(SheetToBooksParserTemplate.class);

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String body = "Illegal argument in request";

        logger.info(ex.getLocalizedMessage());

        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { IndexOutOfBoundsException.class })
    protected ResponseEntity<Object> handleIndexOutOfBounds(RuntimeException ex, WebRequest request) {
        String body = "Cannot parse data from Sheet";

        logger.info(ex.getLocalizedMessage());

        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}