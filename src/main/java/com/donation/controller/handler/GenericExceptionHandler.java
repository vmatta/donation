package com.donation.controller.handler;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Created by Sumit on 8/20/2017.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GenericExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    /**
     * This method is called whenever
     * some generic exception is thrown
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {
        LOGGER.error("Parsing Exception: ", exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ImmutableMap.of(
                "message", "There is some problem in the application. Please contact system administrator"
        ));
    }
}
