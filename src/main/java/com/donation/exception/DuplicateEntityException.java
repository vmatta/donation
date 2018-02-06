package com.donation.exception;

/**
 * Created by Sumit on 8/20/2017.
 */
public class DuplicateEntityException extends RuntimeException {

  public DuplicateEntityException(String message) {
    super(message);
  }
}
