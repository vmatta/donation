package com.donation.exception;

import com.donation.model.Error;
import lombok.Getter;

import java.util.List;

/**
 * Created by Sumit on 8/20/2017.
 */
@Getter
public class InvalidEntityException extends RuntimeException {
    private final List<Error> errors;

    public InvalidEntityException(List<Error> errors) {
        this.errors = errors;
    }
}
