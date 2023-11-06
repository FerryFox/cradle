package com.fox.cradle.configuration.security.config;

public class MyInvalidTokenException extends RuntimeException
{
    public MyInvalidTokenException(String message)
    {
        super(message);
    }
}
