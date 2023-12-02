package com.example.demo.exception.exceptions;

public class BadGatewayException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Gateway Exception";

    public BadGatewayException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public BadGatewayException(String message, Throwable cause) {
        super(DESCRIPTION + ". " + message + cause);
    }
    
}
