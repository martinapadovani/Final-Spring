package com.example.demo.exception.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Request Exception";

    public BadRequestException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(DESCRIPTION + ". " + message + cause);
    }

    
    
}
