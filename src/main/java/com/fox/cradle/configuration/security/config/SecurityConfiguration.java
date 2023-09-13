package com.fox.cradle.configuration.security.config;

import com.fox.cradle.configuration.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> {
                    try {
                        c.init(http);
                    } catch (Exception e) {
                        throw new SecurityException(e);
                    }
                    c.disable();  // If you still want CSRF disabled, but in a more DSL styled manner
                })
                .authorizeHttpRequests(c -> {
                    //list here end points i want to protect for GET requests
                    c.requestMatchers(HttpMethod.GET , "/greet").authenticated();

                    //permits POST for login
                    c.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                    c.requestMatchers(HttpMethod.GET , "/api/**").authenticated();

                    //serves REACT --> TODO make it more specific atm /static/**  dose not work
                    c.requestMatchers(HttpMethod.GET, "/**").permitAll();
                    c.anyRequest().authenticated();
                })
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
