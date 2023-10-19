package com.fox.cradle.features.stampsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.cradle.AbstractMongoDBIntegrationTest;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fox.cradle.configuration.security.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StampControllerTest extends AbstractMongoDBIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void attemptToStamp() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany(); //Ice Cream Company, w@w
        String templateId = "1"; //Ice Cream Template

        MvcResult result = mockMvc.perform(post("/api/stampcard/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(templateId))
                .andExpect(status().isCreated())
                .andReturn();


        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        StampCardResponse stampCard = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        //WHEN



    }

    @Test
    void attemptToComplete() {
    }

    @Test
    void attemptToRedeem() {
    }

    private String getTokenFromIceCreamCompany()
    {
        User user = userRepository.findByEmail("icream@gmail.com").get();
        return jwtService.generateToken(user);
    }
}