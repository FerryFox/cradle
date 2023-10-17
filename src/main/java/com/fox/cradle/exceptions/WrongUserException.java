package com.fox.cradle.exceptions;

public class WrongUserException extends RuntimeException {

    public WrongUserException(String message) {
        super(message);
    }
}