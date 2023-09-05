package com.fox.cradle.configuration.security.auth;

import com.fox.cradle.configuration.security.config.ApplicationConfiguration;
import com.fox.cradle.configuration.security.config.SecurityConfiguration;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest
{

    @InjectMocks
    private AuthenticationService authService;

    @Mock
    private JwtService jwtService;

    @Mock
    private ApplicationConfiguration configuration;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SecurityConfiguration securityConfiguration;

    @Before("")
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //note the test atm uses real implementations. For the to be unit tests, we need to mock the dependencies
    //since I do not mock this, the time of the test is dependent on its success
    //when I mock the response then I do not test the method, because my service uses almost only use internal methods
    //I really should not write anything to my real db, I may forget to remove it when my db gets persistent.

    //@Test
    public void registerTest()
    {
        //given
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("fox");
        request.setEmail("q@q");
        request.setPassword("q");
        request.setReceiveNews(true);


        User user = new User();
        user.setEmail("q@q");
        //when
        AuthenticationResponse response = authService.register(request);
        String jwtToken = jwtService.generateToken(user);
        //then
        assert response.getToken() != null;
        assert response.getToken().equals(jwtToken);
    }

    //@Test
    public void authenticateTest()
    {
        //given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("q@q");
        request.setPassword("q");


        //registerTest();

        //when
        AuthenticationResponse response = authService.authenticate(request);

        //then
        assert response.getToken() != null;
        Assertions.assertTrue(response.getToken().length() > 0);
    }
}
