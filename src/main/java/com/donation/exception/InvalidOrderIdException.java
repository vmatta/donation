package com.donation.exception;

/**
 * Created by Sumit on 8/20/2017.
 */
public class InvalidOrderIdException extends RuntimeException {

  public InvalidOrderIdException(String message) {
    super(message);
  }
}
