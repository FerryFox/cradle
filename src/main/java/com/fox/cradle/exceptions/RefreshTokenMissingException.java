package com.fox.cradle.exceptions;

public class RefreshTokenMissingException extends RuntimeException {

    public RefreshTokenMissingException(String message) {
        super(message);
    }
}