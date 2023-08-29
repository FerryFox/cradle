package com.fox.cradle.configuration.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService
{
    private final BCryptPasswordEncoder _passwordEncoder;

    @Autowired
    public SecurityService(BCryptPasswordEncoder passwordEncoder)
    {
        this._passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String rawPassword) {
        return _passwordEncoder.encode(rawPassword);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return _passwordEncoder.matches(rawPassword, encodedPassword);
    }
}


