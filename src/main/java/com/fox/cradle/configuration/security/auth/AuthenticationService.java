package com.fox.cradle.configuration.security.auth;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.configuration.security.user.User;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService
{
    private final UserRepository _repository;
    private final JwtService _jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request)
    {
        var user = User.builder()
                .firstname(request.getFirstname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .receiveNews(request.isReceiveNews())
                .build();

        _repository.save(user);
        var jwtToken = _jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {

        System.out.println("AuthenticationService.authenticate");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = _repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = _jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
