package com.fox.cradle.features.blog.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.blog.model.BlogEntryDTO;
import com.fox.cradle.features.blog.service.BlogRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void createBlogEntryTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        BlogEntryDTO blogEntryDTO = BlogEntryDTO.builder()
                .title("Test Title")
                .content("Test Content")
                .pictureBase64("data:image/jpg;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAYAAACt")
                .build();

        //WHEN THEN
        MvcResult result = mockMvc.perform(post("/api/blog/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(blogEntryDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        BlogEntryDTO blogEntryDTOResponse = objectMapper.readValue(response, BlogEntryDTO.class);

        Assertions.assertEquals(blogEntryDTO.getTitle(), blogEntryDTOResponse.getTitle());
        Assertions.assertEquals(blogEntryDTO.getContent(), blogEntryDTOResponse.getContent());
        Assertions.assertEquals(blogEntryDTO.getPictureBase64(), blogEntryDTOResponse.getPictureBase64());
        Assertions.assertNotNull(blogEntryDTOResponse.getCreatedDate());
        Assertions.assertNotNull(blogEntryDTOResponse.getAppUser());
    }


    @Test
    void createBlogEntryTestLongName() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        BlogEntryDTO blogEntryDTO = BlogEntryDTO.builder()
                .title("Test Title")
                .content("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                        "\n" +
                        "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                        "\n" +
                        "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                        "\n" +
                        "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.   \n" +
                        "\n" +
                        "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n" +
                        "\n" +
                        "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur")
                .pictureBase64("data:image/jpg;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAYAAACt")
                .build();

        //WHEN THEN
        MvcResult result = mockMvc.perform(post("/api/blog/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(blogEntryDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getLatestBlogEntriesTest() throws Exception {
        //GIVEN
        String token = getTokenFromIceCreamCompany();

        //WHEN THEN
        MvcResult result = mockMvc.perform(get("/api/blog/get-latest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        List<BlogEntryDTO> blogs = objectMapper.readValue(response, new TypeReference<List<BlogEntryDTO>>() {});

        Assertions.assertTrue(blogs.size() > 0);
    }

    private String getTokenFromIceCreamCompany()
    {
        User user = userRepository.findByEmail("icecream@gmail.com").get();
        return jwtService.generateToken(user);
    }
}