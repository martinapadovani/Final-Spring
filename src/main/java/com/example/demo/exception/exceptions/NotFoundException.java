package com.example.demo.exception.exceptions;

public class NotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "Not Found Cloud Vendor Exception";

    //Contiene los constructores que capturan el mensaje generado por la clase Runtime
    public NotFoundException(String message) {
        super(DESCRIPTION + ". " + message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(DESCRIPTION + ". " + message + cause);
    }
    
}
