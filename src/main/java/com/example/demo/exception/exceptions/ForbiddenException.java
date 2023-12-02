package com.example.demo.exception.exceptions;

public class ForbiddenException extends RuntimeException {
    private static final String DESCRIPTION = "Forbidden Exception";

    public ForbiddenException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
    
    public ForbiddenException(String message, Throwable cause) {
        super(DESCRIPTION + ". " + message + cause);
    }
}
