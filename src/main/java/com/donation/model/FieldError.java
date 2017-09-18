package com.donation.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sumit on 8/20/2017.
 */
@Getter
@EqualsAndHashCode
@ToString
public class FieldError {
    private final List<Error> errors;

    public FieldError(){
        this.errors = new ArrayList<>();
    }

    public FieldError addError(Error error){
        this.errors.add(error);
        return this;
    }

    public Boolean hasError(){
        return !this.errors.isEmpty();
    }
}
