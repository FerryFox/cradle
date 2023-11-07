package com.fox.cradle.features.mail.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.blog.model.BlogEntryDTO;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.mail.model.MessageDTO;
import com.fox.cradle.features.mail.model.NewMail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MailControllerIntegrationTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void callsWithWrongUser() throws Exception {
        //GIVEN
        String token = "wrong.token.provided";

        //WHEN THEN

        mockMvc.perform(get("/api/mails/all-my-mails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/mails/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/mails/mark-as-read/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/mails/your-send-mails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());




    }

    @Test
    void getAllUserMailsTest() throws Exception {
        String token = getTokenFromIceCreamCompany();

        //WHEN THEN
        MvcResult result = mockMvc.perform(get("/api/mails/all-my-mails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<MailDTO> blogs = objectMapper.readValue(content, new TypeReference<List<MailDTO>>() {});

        Assertions.assertTrue(blogs.size() > 0);
    }

    @Test
    void getMailCountTest() throws Exception {
        String token = getTokenFromIceCreamCompany();

        //WHEN THEN
        MvcResult result = mockMvc.perform(get("/api/mails/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Integer count = objectMapper.readValue(content, Integer.class);
        Assertions.assertTrue(count > 0);
    }

    @Test
    void markMailAsReadTest() throws Exception {
        String token = getTokenFromIceCreamCompany();
        Long mailId = 1L;

        //WHEN THEN
        mockMvc.perform(post("/api/mails/mark-as-read/" + mailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    void deleteMailTest() throws Exception {
        String token = getTokenFromIceCreamCompany();
        Long mailId = 1L;

        mockMvc.perform(delete("/api/mails/delete/" + mailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMailTestWrongUser() throws Exception {
        String token = getTokenFromIceCreamCompany();
        Long mailId = 4L;

        mockMvc.perform(delete("/api/mails/delete/" + mailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSenderMailTest() throws Exception {
        String token = getTokenFromIceCreamCompany();
        Long mailId = 4L;

        mockMvc.perform(delete("/api/mails/delete-originator/" + mailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSenderMailTestWrongUser() throws Exception {
        String token = getTokenFromIceCreamCompany();
        Long mailId = 1L;

        mockMvc.perform(delete("/api/mails/delete-originator/" + mailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void getYourSendMails() throws Exception {
        String token = getTokenFromIceCreamCompany();

        MvcResult result = mockMvc.perform(get("/api/mails/your-send-mails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<MailDTO> mails = objectMapper.readValue(content, new TypeReference<List<MailDTO>>() {});
        Assertions.assertTrue(mails.size() > 0);
    }

    @Test
    void respondToMailTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        MessageDTO messageDTO = MessageDTO.builder()
                .text("this text will be added to the conversation")
                .originalSender(false)
                .build();

        Long mailId = 1L;

        //WHEN THEN
        mockMvc.perform(post("/api/mails/respond/" + mailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(messageDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void respondToMailTestWrongUser() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        MessageDTO messageDTO = MessageDTO.builder()
                .text("this text will be added to the conversation")
                .originalSender(true)
                .build();

        Long mailId = 1L;

        //WHEN THEN
        mockMvc.perform(post("/api/mails/respond/" + mailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(messageDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void sendMailWithTemplateTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        NewMail newMail = NewMail.builder()
                .receiverId(2L)
                .text("Test Text")
                .templateId(1L)
                .build();

        //WHEN THEN
        mockMvc.perform(post("/api/mails/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(newMail)))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String getTokenFromIceCreamCompany()
    {
        User user = userRepository.findByEmail("icecream@gmail.com").get();
        return jwtService.generateToken(user);
    }
}