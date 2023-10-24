package com.fox.cradle.features.stampsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.stampsystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fox.cradle.configuration.security.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StampControllerTest{

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

        MvcResult create = mockMvc.perform(post("/api/stampcard/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(templateId))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = create.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        StampCardResponse stampCard = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        StampFieldResponse stampField = stampCard.getStampFields().get(0);
        String stampFieldJson = objectMapper.writeValueAsString(stampField);

        //WHEN
        mockMvc.perform(post("/api/stamp/stampThisCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(String.valueOf(stampFieldJson)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "stampMessage":"Stamping successful",
                        "stampAble":true
                        }
                    """))
                .andReturn();

        //THEN
        long id = stampCard.getId();
        MvcResult lookForStamp = mockMvc.perform(get("/api/stampcard/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))

                .andExpect(status().isOk())
                .andReturn();

        String lookJson = lookForStamp.getResponse().getContentAsString();
        StampCardResponse stampCardResponse = objectMapper.readValue(lookJson, new TypeReference<>() {});
        StampFieldResponse stampFieldStamped = stampCardResponse.getStampFields().get(0);
        Assertions.assertTrue(stampFieldStamped.isStamped());
    }

    @Test
    void attemptToCompleteAndThenRedeem() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany(); //Ice Cream Company, w@w
        String templateId = "1"; //Ice Cream Template

        MvcResult create = mockMvc.perform(post("/api/stampcard/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(templateId))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = create.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        StampCardResponse stampCard = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        long id = stampCard.getId();

        //GIVEN
        for ( StampFieldResponse field : stampCard.getStampFields() )
        {
            String stampFieldJson = objectMapper.writeValueAsString(field);
            mockMvc.perform(post("/api/stamp/stampThisCard")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token)
                            .content(stampFieldJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                            "stampMessage":"Stamping successful",
                            "stampAble":true
                            }
                        """));
        }

        //THEN
        MvcResult completedCard = mockMvc.perform(post("/api/stamp/markStampCardAsComplete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(String.valueOf(id)))
                .andExpect(status().isOk())
                .andReturn();

        String completedCardJson = completedCard.getResponse().getContentAsString();
        StampCardResponse completedCardResponse = objectMapper.readValue(completedCardJson, new TypeReference<>() {});
        Assertions.assertTrue(completedCardResponse.isCompleted());

        //THEN
        MvcResult redeemedCard = mockMvc.perform(post("/api/stamp/markStampCardAsRedeemed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(String.valueOf(id)))
                .andExpect(status().isOk())
                .andReturn();

        String redeemedCardJson = redeemedCard.getResponse().getContentAsString();
        StampCardResponse redeemedCardResponse = objectMapper.readValue(redeemedCardJson, new TypeReference<>() {});
        Assertions.assertTrue(redeemedCardResponse.isCompleted());
    }

    private String getTokenFromIceCreamCompany()
    {
        User user = userRepository.findByEmail("icecream@gmail.com").orElseThrow();
        return jwtService.generateToken(user);
    }
}