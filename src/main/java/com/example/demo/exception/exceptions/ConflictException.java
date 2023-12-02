package com.example.demo.exception.exceptions;

public class ConflictException extends RuntimeException {
    private static final String DESCRIPTION = "Conflict Exception";

    public ConflictException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public ConflictException(String message, Throwable cause) {
        super(DESCRIPTION + ". " + message + cause);
    }
    
}
