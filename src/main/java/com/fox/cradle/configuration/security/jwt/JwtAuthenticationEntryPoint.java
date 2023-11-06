package com.fox.cradle.configuration.security.jwt;

import com.fox.cradle.configuration.security.config.MyInvalidTokenException;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        if (authException.getCause() instanceof MyInvalidTokenException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid token");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
