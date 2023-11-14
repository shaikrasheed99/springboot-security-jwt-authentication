package com.springsecurity.controllers;

import com.springsecurity.models.User;
import com.springsecurity.models.UserRepository;
import com.springsecurity.services.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(
                "test_username",
                "test_password",
                "test_role",
                "test_firstname",
                "test_lastname"
        );

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteById(user.getUsername());
    }

    @Test
    void shouldBeAbleToReturnUserDetailsByUsername() throws Exception {
        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/{username}", user.getUsername())
                        .header("Authorization", "Bearer " + token)
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("OK")
        );
    }

    @Test
    void shouldNotBeAbleToAccessAnotherUserDetailsByUsername() throws Exception {
        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        String another_username = "another_username";

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/{username}", another_username)
                        .header("Authorization", "Bearer " + token)
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isForbidden()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("FORBIDDEN")
        );
    }
}