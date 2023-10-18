package com.fox.cradle.configuration.security.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.cradle.AbstractMongoDBIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest extends AbstractMongoDBIntegrationTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldRegisterUser() throws Exception{
        //GIVEN
        RegisterRequest request = RegisterRequest.builder()
                .email("test.user@test.com")
                .firstname("myFirstname")
                .receiveNews(true)
                .password("myPassword")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        //WHEN
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.access_token").isNotEmpty());
    }

    @Test
    void authenticate() throws Exception
    {
        //GIVEN
        RegisterRequest request = RegisterRequest.builder()
                .email("me.bob@test.com")
                .firstname("myFirstname")
                .receiveNews(true)
                .password("myPassword")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());


        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email("me.bob@test.com")
                .password("myPassword")
                .build();

        String authJson = objectMapper.writeValueAsString(authRequest);

        //WHEN THEN
        mockMvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty());
    }

    @Test
    void refreshToken() throws Exception
    {
        //GIVEN
        RegisterRequest request = RegisterRequest.builder()
                .email("test.user@test.com")
                .firstname("myFirstname")
                .receiveNews(true)
                .password("myPassword")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isCreated())
                        .andReturn();

        String responseString = result.getResponse().getContentAsString();
        AuthenticationResponse authenticationResponse = objectMapper.readValue(responseString, AuthenticationResponse.class);
        String token = authenticationResponse.getToken();

        //WHEN THEN
        mockMvc.perform(post("/api/auth/refresh-token")
                .header("Authorization", "Bearer " + token))  // Assuming you're using Bearer tokens
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty());
    }
}