package com.donation.controller.handler;

import com.donation.exception.DuplicateEntityException;
import com.donation.exception.SequenceOrderMissMatchException;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by Sumit on 8/20/2017.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SequenceOrderMissMatchExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(SequenceOrderMissMatchExceptionHandler.class);

    /**
     * This method is called whenever
     * some {@link DuplicateEntityException} exception is thrown
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(SequenceOrderMissMatchException.class)
    public ResponseEntity<Object> handleUniqueEntityException(SequenceOrderMissMatchException exception, WebRequest request) {
        LOGGER.error("Sequence of order id is not proper: ", exception);
        return ResponseEntity.unprocessableEntity().body(ImmutableMap.of(
                "status", "FAILURE",
                "code", 330,
                "message", exception.getMessage()
        ));
    }
}
