package com.fox.cradle.configuration.security.config;

public class TokenCheckException extends RuntimeException
{
    public TokenCheckException(String message)
    {
        super(message);
    }
}
