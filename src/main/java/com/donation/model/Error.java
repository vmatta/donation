package com.donation.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Sumit on 8/20/2017.
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Error {
    private final String field;
    private final String message;
}
