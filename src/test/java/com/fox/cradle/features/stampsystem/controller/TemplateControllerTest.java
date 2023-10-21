package com.fox.cradle.features.stampsystem.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stampsystem.model.template.TemplateEdit;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TemplateControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppUserService appUserService;

    @Autowired
    JwtService jwtService;

    static final String STANDARD_NEW_TEMPLATE =  """
                {
                    "name":"CoolTemplate",
                    "promise":"free beer",
                    "description":"buy some get some",
                    "image":"data:image/jpeg;base64,/9j/4AAQSkZJRUDNf//Z",
                    "fileName":"P1010050.JPG",
                    "defaultCount":10,
                    "stampCardCategory":"DRINK",
                    "stampCardSecurity":"TRUSTUSER",
                    "stampCardStatus":"ACTIVE",
                    "securityTimeGateDuration":"PT0H",
                    "expirationDate":"2024-10-18T11:59:56.000Z"
                }
                """;
    @Test
    void createStandardTemplate() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

        //When
         mockMvc.perform(post("/api/templates/new-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(STANDARD_NEW_TEMPLATE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("CoolTemplate"))
                .andExpect(jsonPath("$.description").value("buy some get some"))
                .andExpect(jsonPath("$.defaultCount").value(10))
                .andExpect(jsonPath("$.stampCardCategory").value("DRINK"))
                .andExpect(jsonPath("$.stampCardSecurity").value("TRUSTUSER"))
                .andExpect(jsonPath("$.stampCardStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.expirationDate").value("2024-10-18T11:59:56.000Z"))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.lastModifiedDate").exists());
    }

    @Test
    void getTemplateById() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

        MvcResult result = mockMvc.perform(post("/api/templates/new-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(STANDARD_NEW_TEMPLATE))
                .andExpect(status().isCreated())
                .andReturn();

        int returnedId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        //WHEN THEN
        mockMvc.perform(get("/api/templates/" + returnedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("CoolTemplate"))
                .andExpect(jsonPath("$.description").value("buy some get some"))
                .andExpect(jsonPath("$.defaultCount").value(10))
                .andExpect(jsonPath("$.stampCardCategory").value("DRINK"))
                .andExpect(jsonPath("$.stampCardSecurity").value("TRUSTUSER"))
                .andExpect(jsonPath("$.stampCardStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.expirationDate").value("2024-10-18T11:59:56.000Z"))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.lastModifiedDate").exists());

    }

    @Test
    void getAllTemplates() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

       mockMvc.perform(post("/api/templates/new-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(STANDARD_NEW_TEMPLATE))
                .andExpect(status().isCreated())
                .andReturn();

        //WHEN
        MvcResult list = mockMvc.perform(get("/api/templates/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = list.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<TemplateResponse> templates = objectMapper.readValue(jsonResponse, new TypeReference<List<TemplateResponse>>(){});
        TemplateResponse lastTemplate = templates.get(templates.size() - 1);

        //THEN
        Assertions.assertTrue(templates.size() > 0);
        Assertions.assertEquals("CoolTemplate", lastTemplate.getName());
        Assertions.assertEquals("buy some get some", lastTemplate.getDescription());
        Assertions.assertEquals(10, lastTemplate.getDefaultCount());
        Assertions.assertEquals("DRINK", lastTemplate.getStampCardCategory().toString());
        Assertions.assertEquals("TRUSTUSER", lastTemplate.getStampCardSecurity().toString());
        Assertions.assertEquals("ACTIVE", lastTemplate.getStampCardStatus().toString());
        Assertions.assertEquals("2024-10-18T11:59:56.000Z", lastTemplate.getExpirationDate());
        Assertions.assertNotNull(lastTemplate.getCreatedDate());
    }

    @Test
    void getAllPublicTemplates() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

        MvcResult result = mockMvc.perform(post("/api/templates/new-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(STANDARD_NEW_TEMPLATE))
                .andExpect(status().isCreated())
                .andReturn();

        //WHEN
        MvcResult list = mockMvc.perform(get("/api/templates/all/public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = list.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<TemplateResponse> templates = objectMapper.readValue(jsonResponse, new TypeReference<List<TemplateResponse>>(){});

        boolean allPublicOrNone = templates.stream()
                .allMatch(template -> "PUBLIC".equals(template.getStampCardStatus().toString()));

        //THEN
        Assertions.assertTrue(allPublicOrNone);
    }

    @Test
    void getMyTemplates() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

        MvcResult result = mockMvc.perform(post("/api/templates/new-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(STANDARD_NEW_TEMPLATE))
                .andExpect(status().isCreated())
                .andReturn();

        //WHEN
        MvcResult list = mockMvc.perform(get("/api/templates//my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = list.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<TemplateResponse> templates = objectMapper.readValue(jsonResponse, new TypeReference<List<TemplateResponse>>(){});

        //I can not get the user id since it is not in the response

        //THEN
        Assertions.assertTrue( templates.size() > 0);
    }

    @Test
    void deleteTemplate() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();


        MvcResult result = mockMvc.perform(post("/api/templates/new-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(STANDARD_NEW_TEMPLATE))
                .andExpect(status().isCreated())
                .andReturn();

        int returnedId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        //WHEN
        mockMvc.perform(delete("/api/templates/delete/" + returnedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    void updateTemplateTest() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();


        MvcResult result = mockMvc.perform(post("/api/templates/new-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(STANDARD_NEW_TEMPLATE))
                .andExpect(status().isCreated())
                .andReturn();

        String returnedId = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();
        TemplateResponse templateResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), TemplateResponse.class);
        Instant now = Instant.now();

        TemplateEdit templateEdit = TemplateEdit.builder()
                .id(returnedId)
                .name("updatedName")
                .promise("updatedPromise")
                .description("updatedDescription")
                .defaultCount("20")

                .image(templateResponse.getImage())
                .createdBy("updatedCreatedBy")
                .stampCardSecurity("TRUSTUSER")
                .stampCardStatus("ACTIVE")
                .stampCardCategory("FOOD")

                .expirationDate("2029-10-18T11:59:56.000Z")
                .lastModifiedDate(now.toString())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        //WHEN
        mockMvc.perform(put("/api/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(templateEdit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("updatedName"))
                .andExpect(jsonPath("$.description").value("updatedDescription"))
                .andExpect(jsonPath("$.defaultCount").value(20))
                .andExpect(jsonPath("$.stampCardCategory").value("FOOD"))
                .andExpect(jsonPath("$.stampCardSecurity").value("TRUSTUSER"))
                .andExpect(jsonPath("$.stampCardStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.expirationDate").value("2029-10-18T11:59:56.000Z"))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.lastModifiedDate").exists());

    }

    @Test
    void getTemplatesByCategory() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

        mockMvc.perform(get("/api/templates/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("FOOD"))
                .andExpect(jsonPath("$[1]").value("DRINK"))
                .andExpect(jsonPath("$[2]").value("ENTERTAINMENT"))
                .andExpect(jsonPath("$[3]").value("OTHER"));
    }

    @Test
    void getTemplatesBySecurity() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

        //THEN WHEN

        mockMvc.perform(get("/api/templates/security")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("TRUSTUSER"))
                .andExpect(jsonPath("$[1]").value("TIMEGATE"))
                .andExpect(jsonPath("$[2]").value("LOCATIONGATE"));
    }

    @Test
    void getTemplatesByStatus() throws Exception {
        //GIVEN
        String token = SaveUsersAndGetToken();

        //THEN WHEN
        mockMvc.perform(get("/api/templates/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("ACTIVE"))
                .andExpect(jsonPath("$[1]").value("INACTIVE"))
                .andExpect(jsonPath("$[2]").value("PRIVATE"))
                .andExpect(jsonPath("$[3]").value("PUBLIC"));

    }

    private String SaveUsersAndGetToken()
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User securityUser = new User();
        securityUser.setEmail("test@gmail.com");
        securityUser.setPassword(passwordEncoder.encode("myPassword"));
        if(userRepository.findByEmail(securityUser.getEmail()).isEmpty()){
            userRepository.save(securityUser);

            AppUser appUser = AppUser.builder()
                    .appUserName("Ice Man")
                    .appUserEmail("test@gmail.com")
                    .myStampCards(null)
                    .additionalInfo(null)
                    .receiveNews(true)
                    .nameIdentifier("1234-1234-1234")
                    .build();

            appUserService.saveAppUser(appUser);
        }

        var result =  jwtService.generateToken(securityUser);
        System.out.println(result);
        return result;
    }
}