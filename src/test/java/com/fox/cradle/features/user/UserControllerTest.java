package com.fox.cradle.features.user;

import com.fox.cradle.features.user.User;
import com.fox.cradle.features.user.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before("")
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        User mockUser = new User();
        mockUser.setUsername("Alice");
        mockUser.setPassword("password1");

        when(userService.getUserByUsername("Alice")).thenReturn(mockUser);

        mockMvc.perform(get("/users/Alice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Alice"))
                .andExpect(jsonPath("$.password").value("password1"));
    }
}
