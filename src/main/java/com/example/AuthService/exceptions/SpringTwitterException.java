package com.example.AuthService.exceptions;

public class SpringTwitterException extends RuntimeException{

    public SpringTwitterException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringTwitterException(String exMessage) {
        super(exMessage);
    }
}
