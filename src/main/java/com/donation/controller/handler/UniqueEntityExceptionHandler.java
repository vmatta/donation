package com.donation.controller.handler;

import com.donation.exception.DuplicateEntityException;
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
 * Created by Vijay on 8/20/2017.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UniqueEntityExceptionHandler {

  public static final Logger LOGGER = LoggerFactory.getLogger(UniqueEntityExceptionHandler.class);

  /**
   * This method is called whenever some {@link DuplicateEntityException} exception is thrown
   */
  @ExceptionHandler(DuplicateEntityException.class)
  public ResponseEntity<Object> handleUniqueEntityException(DuplicateEntityException exception, WebRequest request) {
    LOGGER.error("Integrity violation Exception: ", exception);
    return ResponseEntity.unprocessableEntity().body(ImmutableMap.of(
        "status", "FAILURE",
        "code", 330,
        "message", exception.getMessage()
    ));
  }
}
