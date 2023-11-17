package com.fox.cradle.configuration.security.config;

public class InvalidTokenException extends RuntimeException
{
    public InvalidTokenException(String message)
    {
        super(message);
    }
}
