package com.donation.controller.handler;

import com.donation.exception.DateParseException;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
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
public class ParsingExceptionHandler {

  public static final Logger LOGGER = LoggerFactory.getLogger(ParsingExceptionHandler.class);

  /**
   * This method is called whenever Date parsing is throwing any error
   */
  @ExceptionHandler(DateParseException.class)
  public ResponseEntity<Object> handleValidationException(DateParseException dateParseException, WebRequest request) {
    LOGGER.error("Parsing Exception: ", dateParseException);
    return ResponseEntity.unprocessableEntity().body(ImmutableMap.of(
        "status", "FAILURE",
        "code", 141,
        "message", dateParseException.getMessage()
    ));
  }
}
