package com.fox.cradle.features.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AppUserControllerTest {

    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);

        // Create instance of userController with mocked userService
        userController = new UserController(userService);

        // Setup mockMvc with userController instance
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        AppUser mockAppUser = new AppUser();
        mockAppUser.setUsername("Alice");
        mockAppUser.setPassword("password");

        when(userService.getUserByUsername("Alice")).thenReturn(mockAppUser);

        mockMvc.perform(get("/api/user/username/Alice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Alice"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void testAddUser() throws Exception {
        AppUser mockAppUser = new AppUser();
        mockAppUser.setUsername("Alice");
        mockAppUser.setPassword("password");

        when(userService.addUser(mockAppUser)).thenReturn(mockAppUser);

        mockMvc.perform(post("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Alice\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Alice"))
                .andExpect(jsonPath("$.password").value("password"));
    }
}
