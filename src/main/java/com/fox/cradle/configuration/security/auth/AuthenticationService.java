package com.fox.cradle.configuration.security.auth;

import com.fox.cradle.configuration.security.SecuritySender;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.configuration.security.user.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService
{
    private final UserRepository _userRepository;
    private final JwtService _jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public AuthenticationResponse register(RegisterRequest request)
    {
        var user = User.builder()
                .firstname(request.getFirstname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .receiveNews(request.isReceiveNews())
                .build();


        User savedUser = _userRepository.save(user);
        //send event to if user is registered
        if(savedUser != null)
        {
            eventPublisher.publishEvent(new SecuritySender(this, savedUser));
        }

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

        var user = _userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = _jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse refreshToken(HttpServletRequest request)
    {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String email;

        if (authHeader == null ||!authHeader.startsWith("Bearer "))
        {
            //that is a problem? the token is not in response
            throw new RuntimeException("Refresh token is missing");
        }

        token = authHeader.substring(7);

        email = _jwtService.extractUsername(token);

        if (email != null)
        {
            var user = _userRepository.findByEmail(email).orElseThrow();
            var jwtToken = _jwtService.generateLongLiveToken(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        else
        {
            throw new RuntimeException("Refresh token is invalid");
        }
    }
}
