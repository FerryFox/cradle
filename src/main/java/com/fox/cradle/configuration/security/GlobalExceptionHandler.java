package com.fox.cradle.configuration.security;

import com.fox.cradle.configuration.security.config.MyInvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//dose not work atm
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MyInvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(MyInvalidTokenException ex)
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

