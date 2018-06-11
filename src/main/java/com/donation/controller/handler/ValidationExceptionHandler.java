package com.donation.controller.handler;

import com.donation.exception.InvalidEntityException;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Vijay on 8/20/2017.
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * This method being called when validation of an entity object throws validation error
   */
  @ExceptionHandler(InvalidEntityException.class)
  public ResponseEntity<Object> handleValidationException(InvalidEntityException validationException, WebRequest request) {
    logger.error("ValidationException: ", validationException);
    return ResponseEntity.unprocessableEntity().body(ImmutableMap.of(
        "status", "FAILURE",
        "code", 400,
        "errors", validationException.getErrors()
    ));
  }
}
