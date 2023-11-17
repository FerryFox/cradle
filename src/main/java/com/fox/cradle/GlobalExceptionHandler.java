package com.fox.cradle;

import com.fox.cradle.configuration.security.config.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//dose not work atm
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex)
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

