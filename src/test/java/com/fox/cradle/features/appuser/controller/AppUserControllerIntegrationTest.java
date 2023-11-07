package com.fox.cradle.features.appuser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.cradle.configuration.security.auth.AuthenticationService;
import com.fox.cradle.configuration.security.auth.RegisterRequest;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AdditionalInfo;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.appuser.service.AppUserService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AppUserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserService appUserService;

    @Test
    void TestWrongUser() throws Exception {
        //GIVEN
        String token = "invalid.token.test";

        //WHEN & THEN
        mockMvc.perform(get("/api/user/my-additional-info")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/user/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/user/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/user/friends")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/add-friend/" + 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/user/additional-info")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/user/get-users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/user/delete-friend/" + 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAdditionalInfoTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN
        MvcResult result = mockMvc.perform(get("/api/user/my-additional-info")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        AddInfoDTO addInfoDTO = objectMapper.readValue(result.getResponse().getContentAsString(), AddInfoDTO.class);
        Assertions.assertEquals(1L, addInfoDTO.getId());
        Assertions.assertEquals("We are a small ice cream company from Berlin. We love ice cream and we want to share our passion with you. We are looking forward to your visit!", addInfoDTO.getBio());
        Assertions.assertTrue(addInfoDTO.getName().contains("#"));
    }

    @Test
    void getMeWithFriendsAndAddInfoTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN
        MvcResult result = mockMvc.perform(get("/api/user/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        AppUserDTO appUserDTO = objectMapper.readValue(result.getResponse().getContentAsString(), AppUserDTO.class);
        Assertions.assertEquals(1L, appUserDTO.getId());
        Assertions.assertEquals("Ice Cream Company", appUserDTO.getAppUserName());
        Assertions.assertEquals("icecream@gmail.com", appUserDTO.getAppUserEmail());
        Assertions.assertNotNull(appUserDTO.getNameIdentifier());

        Assertions.assertNotNull(appUserDTO.getAddInfoDTO());
        Assertions.assertEquals(1L, appUserDTO.getAddInfoDTO().getId());

        Assertions.assertNotNull(appUserDTO.getFriends());
        Assertions.assertNull(appUserDTO.getMails());
        Assertions.assertNull(appUserDTO.getTemplates());
    }

    @Test
    void getUser() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN
        MvcResult result = mockMvc.perform(get("/api/user/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        AppUserDTO appUserDTO = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AppUserDTO.class);

        Assertions.assertEquals(1L, appUserDTO.getId());
        Assertions.assertNotNull(appUserDTO.getAddInfoDTO());
        Assertions.assertNotNull(appUserDTO.getTemplates());
        Assertions.assertNotNull(appUserDTO.getFriends());

        Assertions.assertNull(appUserDTO.getMails());
    }

    @Test
    void getFriends() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN
        MvcResult result = mockMvc.perform(get("/api/user/friends")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        AppUserDTO[] appUserDTOS = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AppUserDTO[].class);

        Assertions.assertEquals(1, appUserDTOS.length);

        AppUserDTO appUserDTO = appUserDTOS[0];

        Assertions.assertNotNull(appUserDTO.getAddInfoDTO());
        Assertions.assertNull(appUserDTO.getTemplates());
        Assertions.assertNull(appUserDTO.getMails());
        Assertions.assertNull(appUserDTO.getFriends());
    }

    @Test
    void addFriend() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN
        MvcResult result = mockMvc.perform(post("/api/user/add-friend/" + 2L)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        //Return the friend
        AppUserDTO appUserDTO = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AppUserDTO.class);

        Assertions.assertEquals(2L, appUserDTO.getId());
        Assertions.assertNotNull(appUserDTO.getAddInfoDTO());

        Assertions.assertNull(appUserDTO.getTemplates());
        Assertions.assertNull(appUserDTO.getMails());
        assertTrue(appUserDTO.getFriends() == null || appUserDTO.getFriends().isEmpty());
    }

    @Test
    void saveAdditionalInfoTest() throws Exception {
        //GIVEN
        RegisterRequest registerRequestIce = RegisterRequest.builder()
                .firstname("Test User")
                .email("klrt@google.com")
                .password("Babel678")
                .receiveNews(true)
                .build();

        //Event triggers the creation of an appUser
        authService.register(registerRequestIce);
        AppUser appUser = appUserService.findUserByEmail(registerRequestIce.getEmail()).orElseThrow();

        User user = userRepository.findByEmail(appUser.getAppUserEmail()).get();
        String token = jwtService.generateToken(user);


        //WHEN
        AddInfoDTO addInfoDTO = AddInfoDTO.builder()
                .id(appUser.getAdditionalInfo().getId())
                .bio("change")
                .name("changed name")
                .status("change status")
                .picture("data:image/png;base64,change")
                .build();

        MvcResult result = mockMvc.perform(post("/api/user/additional-info")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addInfoDTO)))
                .andExpect(status().isOk())
                .andReturn();

        AddInfoDTO resultAddInfoDTO = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AddInfoDTO.class);

        //THEN
        Assertions.assertEquals(addInfoDTO.getId(), resultAddInfoDTO.getId());
        Assertions.assertEquals(addInfoDTO.getBio(), resultAddInfoDTO.getBio());
        Assertions.assertEquals(addInfoDTO.getName(), resultAddInfoDTO.getName());
        Assertions.assertEquals(addInfoDTO.getStatus(), resultAddInfoDTO.getStatus());
        Assertions.assertEquals(addInfoDTO.getPicture(), resultAddInfoDTO.getPicture());
    }

    @Test
    void getUsersTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN
        MvcResult result = mockMvc.perform(get("/api/user/get-users")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        AppUserDTO[] appUserDTOS = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AppUserDTO[].class);

        boolean DoesNotContainIceCreamCompany = Arrays.stream(appUserDTOS)
                .noneMatch(x -> x.getAppUserEmail().equals("icecream@gmail.com"));

        Assertions.assertTrue(appUserDTOS.length > 1);
        Assertions.assertTrue(DoesNotContainIceCreamCompany);
    }

    @Test
    @DirtiesContext
    void deleteFriendTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN THEN
        mockMvc.perform(delete("/api/user/delete-friend/" + 2L)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String getTokenFromIceCreamCompany()
    {
        User user = userRepository.findByEmail("icecream@gmail.com").get();
        return jwtService.generateToken(user);
    }
}