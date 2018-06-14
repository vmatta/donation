package com.donation.exception;

/**
 * Created by Sumit on 6/14/2018.
 */
public class MailException extends RuntimeException{
    public MailException(Throwable throwable){
        super(throwable);
    }
}
