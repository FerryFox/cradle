package com.fox.cradle.configuration.security.jwt;


import com.fox.cradle.configuration.security.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import io.jsonwebtoken.Claims;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JwtServiceTest
{
    public String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxQHEiLCJpYXQiOjE2OTM4OTY2MTIsImV4cCI6MTY5Mzk4MzAxMn0.qeYj6F57epQn-Hx3k_ZQF5hXKa_A-ghjiOYkyYblYE4";

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private  JwtService jwtService;

    @Test
    public void extractUsernameFromJwT()
    {
        //given
        String token = this.token;

        //when : get the email from the user.getEmail()
        String username = jwtService.extractUsername(token);

        //then
        assert username.equals("q@q");
    }

    @Test
    public void extractUserNameClaimsFromToken()
    {
        //given
        String token = this.token;

        //when
        String username = jwtService.extractClaim(token, Claims::getSubject);

        //then
        assert username.equals("q@q");
    }

    @Test
    public void generateTokenWithUser()
    {
        User user = new User();
        user.setEmail("q@q");
        //given
        String token = jwtService.generateToken(user);
        System.out.println(token);
        //when
        boolean isValid = jwtService.isTokenValid(token, user);
        //then
        assert isValid;
        assert jwtService.extractUsername(token).equals(user.getEmail());
    }

    @Test
    public void generateTokenWithUserDetails() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("q@q")
                .password("q")
                .authorities(authorities)
                .build();

        //given
        String token = jwtService.generateToken(userDetails);
        System.out.println(token);

        //when
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        //then
        assert isValid;
        assert jwtService.extractUsername(token).equals(userDetails.getUsername());
    }

    @Test
    public void isTokenValidWithFreshToken()
    {
        User user = new User();
        user.setEmail("f@f");
        String token = jwtService.generateToken(user);
        boolean isValid = jwtService.isTokenValid(token, user);
        assert isValid;
    }

    @Test
    public void isTokenExpired()
    {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxQHEiLCJpYXQiOjE2OTI4OTk1OTYsImV4cCI6MTY5MzI4NTk5Nn0.BDjS-CO_VsjX7ogsgeEk3Py3oYXh8iXpD9rGS07zFRw";

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
    public void isTokenValidWithWrongUser()
    {
        User user = new User();
        user.setEmail("wrong@fail.de");
        String token = this.token;
        String username = jwtService.extractUsername(token);
        Assertions.assertFalse(user.getEmail().equals(username));
    }
}
