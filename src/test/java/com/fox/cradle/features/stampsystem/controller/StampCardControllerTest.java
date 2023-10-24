package com.fox.cradle.features.stampsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.service.AppUserRepository;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StampCardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    JwtService jwtService;

    @Test
    void createGetAndDeleteStampCard() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();
        String templateId = "1"; //Ice Cream Template

        //WHEN THEN
        MvcResult result = mockMvc.perform(post("/api/stampcard/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(templateId))
            .andExpect(status().isCreated())
            .andReturn();

        int returnedId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/api/stampcard/" + returnedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/stampcard/" + returnedId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    void getAllActiveStampCardsNoFields() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();
        String templateId = "1";

        MvcResult result = mockMvc.perform(post("/api/stampcard/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(templateId))
                .andExpect(status().isCreated())
                .andReturn();

        int returnedId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        //WHEN THEN
        MvcResult list = mockMvc.perform(get("/api/stampcard/allactive")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andReturn();

        String jsonResponse = list.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<StampCardResponse> stampCards = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        StampCardResponse stampCardResponse = stampCards.stream().filter(t -> t.getId() == returnedId).findFirst().get();

        Assertions.assertTrue(stampCards.size() > 0);
        Assertions.assertEquals(returnedId, stampCardResponse.getId());
        Assertions.assertNull(stampCardResponse.getStampFields());
    }

    @Test
    void getAllStampCardsArchived() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN THEN
        MvcResult list = mockMvc.perform(get("/api/stampcard/archived")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk()).andReturn();

        String jsonResponse = list.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<StampCardResponse> stampCards = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        boolean onlyRedeemed = stampCards.stream().allMatch(t -> t.isRedeemed());
        Assertions.assertTrue(onlyRedeemed);
    }

    private String getTokenFromIceCreamCompany()
    {
        User user = userRepository.findByEmail("icecream@gmail.com").get();
        return jwtService.generateToken(user);
    }
}