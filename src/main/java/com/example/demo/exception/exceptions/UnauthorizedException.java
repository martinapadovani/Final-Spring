package com.example.demo.exception.exceptions;

public class UnauthorizedException extends RuntimeException {
    private static final String DESCRIPTION = "Unauthorized Exception - 401";

    public UnauthorizedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(DESCRIPTION + ". " + message + cause);
    }
    
}
