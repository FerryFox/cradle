package com.fox.cradle.configuration.security.auth;

public interface IAuthenticationService
{
     AuthenticationResponse register(RegisterRequest request);
     AuthenticationResponse authenticate(AuthenticationRequest request);
}
