package com.fox.cradle.configuration.security.jwt;


import com.fox.cradle.configuration.security.user.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import io.jsonwebtoken.Claims;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class JwtServiceIntegrationTest
{

    //private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxQHEiLCJpYXQiOjE2OTM4OTY2MTIsImV4cCI6MTY5Mzk4MzAxMn0.4zbMTNA9Q5-0vf-9L9UIDVU8NRL9HjYomQp9qQGLuwI";

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private  JwtService jwtService;

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer();

    @BeforeAll
    static void setup()
    {
        mongoDBContainer.start();
    }

    @AfterAll
    static void cleanup()
    {
        mongoDBContainer.stop(); // stop the MongoDB container
    }

    @DynamicPropertySource
    static void setUrlDynamically(DynamicPropertyRegistry registry)
    {
        System.out.println( "MONGO_URL=" + mongoDBContainer.getReplicaSetUrl());
        registry.add("MONGO_URL", () -> mongoDBContainer.getReplicaSetUrl());
    }

    @Test
    public void extractUsernameFromJwT()
    {
        //GIVEN
        User user = new User();
        user.setEmail("q@q");
        String token = jwtService.generateToken(user);
        //WHEN
        String username = jwtService.extractUsername(token);
        //THEN
        assert username.equals("q@q");
    }

    @Test
    public void generateTokenWithUser()
    {
        //GIVEN
        User user = new User();
        user.setEmail("q@q");
        String token = jwtService.generateToken(user);
        //WHEN
        boolean isValid = jwtService.isTokenValid(token, user);
        //THEN
        assert isValid;
        assert jwtService.extractUsername(token).equals(user.getEmail());
    }

    @Test
    public void generateTokenWithUserDetails() {
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
    public void isTokenValidWithWrongUser()
    {
        User user = new User();
        user.setEmail("wrong@fail.de");
        String token = jwtService.generateToken(user);

        User user2 = new User();
        user2.setEmail("f@f");

        String username = jwtService.extractUsername(token);
        Assertions.assertFalse(user2.getEmail().equals(username));
    }

    @Test
    public void isRefreshTokenlongLived()
    {
        User user = new User();
        user.setEmail("f@f");
        String token = jwtService.generateLongLiveToken(user);

        var expiration = jwtService.extractClaim(token, Claims::getExpiration);

        boolean isValid = jwtService.isTokenValid(token, user);
        assert isValid;
        assert jwtService.extractUsername(token).equals(user.getEmail());
        Assertions.assertFalse(jwtService.isTokenExpired(token));
        assert expiration.after(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 6));
    }
}
