package com.example.demo.exception;

import javax.persistence.Entity;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ModelResponse {

    private final String exception;
    private final String message;
    private final Throwable cause;
    private final HttpStatus httpStatus;
    
    public ModelResponse(Exception exception, Throwable cause, HttpStatus httpStatus) {
        this.exception = exception.getClass().getSimpleName(); //Desde la excepcion extraigo su tipo
        this.message = exception.getMessage();// Y su mensaje (completado en el servicio)
        this.cause = cause;
        this.httpStatus = httpStatus;
    }
    
}
