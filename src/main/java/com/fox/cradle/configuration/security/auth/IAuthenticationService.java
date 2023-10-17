package com.fox.cradle.configuration.security.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService
{
     AuthenticationResponse register(RegisterRequest request);
     AuthenticationResponse authenticate(AuthenticationRequest request);
     AuthenticationResponse refreshToken(HttpServletRequest request);
}
