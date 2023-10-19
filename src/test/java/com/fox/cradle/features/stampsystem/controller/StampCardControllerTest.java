package com.fox.cradle.features.stampsystem.controller;

import com.fox.cradle.AbstractMongoDBIntegrationTest;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.service.AppUserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StampCardControllerTest extends AbstractMongoDBIntegrationTest {

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
        String token = getTokenFromIceCreamCompany(); //Ice Cream Company, w@w
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

        //WHEN THEN
        MvcResult value = mockMvc.perform(get("/api/stampcard/allactive")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(result -> result.getResponse().getContentAsString().contains("Ice Cream"))
            .andExpect(result -> result.getResponse().getContentAsString().contains("Cinema"))
            .andReturn();

        System.out.println(value.getResponse().getContentAsString());

    }

    @Test
    void getAllStampCardsArchived() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN THEN
        MvcResult result = mockMvc.perform(get("/api/stampcard/archived")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertFalse(content.contains("Ice Cream"));
    }

    private String getTokenFromIceCreamCompany()
    {
        User user = userRepository.findByEmail("icream@gmail.com").get();
        return jwtService.generateToken(user);
    }
}