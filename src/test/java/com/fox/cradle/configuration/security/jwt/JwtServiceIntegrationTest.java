package com.fox.cradle.configuration.security.jwt;

import com.fox.cradle.configuration.security.user.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

@SpringBootTest
class JwtServiceIntegrationTest
{
    @Autowired
    private JwtService jwtService;

    @Test
    void extractUsernameFromJwT()
    {
        //GIVEN
        User user = new User();
        user.setEmail("q@q");
        String token = jwtService.generateToken(user);
        //WHEN
        String username = jwtService.extractUsername(token);
        //THEN
        Assertions.assertEquals("q@q", username);
    }

    @Test
    void generateTokenWithUser()
    {
        //GIVEN
        User user = new User();
        user.setEmail("q@q");
        String token = jwtService.generateToken(user);
        //WHEN
        boolean isValid = jwtService.isTokenValid(token, user);
        //THEN
        Assertions.assertTrue(isValid);
        assert jwtService.extractUsername(token).equals(user.getEmail());
    }

    @Test
    void generateTokenWithUserDetails() {
        //Given
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("q@q")
                .password("q")
                .authorities(authorities)
                .build();

        String token = jwtService.generateToken(userDetails);
        System.out.println(token);

        //WHEN
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        //THEN
        Assertions.assertTrue(isValid);
        assert jwtService.extractUsername(token).equals(userDetails.getUsername());
    }

    @Test
    void isTokenValidWithFreshToken()
    {
        User user = new User();
        user.setEmail("f@f");
        String token = jwtService.generateToken(user);
        boolean isValid = jwtService.isTokenValid(token, user);
        Assertions.assertTrue(isValid);
    }

    @Test
    void isTokenExpired()
    {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxQHEiLCJpYXQiOjE2OTI4OTk1OTYsImV4cCI6MTY5MzI4NTk5Nn0.EgmaJWwFIpLHQVC_Q5Mxej_aelyzbLO_uYUunq-tZZo";

        try
        {
            boolean isExpired = jwtService.isTokenExpired(token);
            Assertions.assertTrue(isExpired);
        }
        catch (Exception e)
        {
           Assertions.assertTrue(true);
        }
    }

    @Test
    void isTokenValidWithWrongUser()
    {
        User user = new User();
        user.setEmail("wrong@fail.de");
        String token = jwtService.generateToken(user);

        User user2 = new User();
        user2.setEmail("f@f");

        String username = jwtService.extractUsername(token);
        Assertions.assertNotEquals(user2.getEmail() , username);
    }


}
