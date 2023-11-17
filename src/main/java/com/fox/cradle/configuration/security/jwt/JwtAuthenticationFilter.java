package com.fox.cradle.configuration.security.jwt;

import com.fox.cradle.configuration.security.config.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /*
    The given code is a portion of a custom filter implementation, specifically for a method
    doFilterInternal that is often seen in the context of Spring Framework's OncePerRequestFilter.
    This filter ensures that a specific piece of code is executed once and only once for each request.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        //changes from /api/v1/auth to /api/auth
        if (request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");

        //stops searching for the userDetails if the token is null or doesn't start with "Bearer "
        if (authHeader == null ||!authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        //gets the user's email from the token and sets the userDetails to the user's email
        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (InvalidTokenException ex) {
            // Here you catch your custom exception and set the 401 Unauthorized response
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid token");
            // Log the exception here if you need to
            // Do not call filterChain.doFilter(request, response) here, the response is already complete
        }
    }
}
