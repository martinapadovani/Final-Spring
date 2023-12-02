package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.exception.exceptions.BadGatewayException;
import com.example.demo.exception.exceptions.BadRequestException;
import com.example.demo.exception.exceptions.ConflictException;
import com.example.demo.exception.exceptions.ForbiddenException;
import com.example.demo.exception.exceptions.NotFoundException;
import com.example.demo.exception.exceptions.UnauthorizedException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NotFoundException.class}) 
    @ResponseBody
    public ResponseEntity<Object> handleNotFoundException (NotFoundException NotFoundException){
        NotFoundException.printStackTrace();
        ModelResponse modelResponse = new ModelResponse(
            NotFoundException, 
            NotFoundException.getCause(),
            HttpStatus.NOT_FOUND
            
        );
        return new ResponseEntity<>(modelResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        BadRequestException.class,
        org.springframework.dao.DuplicateKeyException.class,
        org.springframework.web.bind.support.WebExchangeBindException.class,
        org.springframework.http.converter.HttpMessageNotReadableException.class,
        org.springframework.web.server.ServerWebInputException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleBadRequestException (BadRequestException BadRequestException){
        BadRequestException.printStackTrace();
        ModelResponse modelResponse = new ModelResponse(
            BadRequestException, 
            BadRequestException.getCause(),
            HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(modelResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
        ConflictException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleConflictException (ConflictException ConflictException){
        ConflictException.printStackTrace();
        ModelResponse modelResponse = new ModelResponse(
            ConflictException, 
            ConflictException.getCause(),
            HttpStatus.CONFLICT
        );
        return new ResponseEntity<>(modelResponse, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
        ForbiddenException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleForbiddenException (ForbiddenException ForbiddenException){
        ForbiddenException.printStackTrace();
        ModelResponse modelResponse = new ModelResponse(
            ForbiddenException, 
            ForbiddenException.getCause(),
            HttpStatus.FORBIDDEN
        );
        return new ResponseEntity<>(modelResponse, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler({
        BadGatewayException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleBadGatewayException (BadGatewayException BadGatewayException){
        BadGatewayException.printStackTrace();
        ModelResponse modelResponse = new ModelResponse(
            BadGatewayException, 
            BadGatewayException.getCause(),
            HttpStatus.BAD_GATEWAY
        );
        return new ResponseEntity<>(modelResponse, HttpStatus.BAD_GATEWAY);
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
        UnauthorizedException.class,
    })
    @ResponseBody
    public void unauthorizedRequest(UnauthorizedException UnauthorizedException) {
        UnauthorizedException.printStackTrace();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleInternalServerError(Exception exception){
        exception.printStackTrace();

        ModelResponse modelResponse = new ModelResponse(
            exception, 
            exception.getCause(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(modelResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
