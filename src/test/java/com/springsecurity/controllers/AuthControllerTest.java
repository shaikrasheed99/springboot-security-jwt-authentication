package com.springsecurity.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecurity.helpers.request.LoginRequest;
import com.springsecurity.helpers.request.SignupRequest;
import com.springsecurity.models.User;
import com.springsecurity.models.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public UserRepository userRepository;

    public SignupRequest signupRequest;

    @BeforeEach
    public void setUp() {
        signupRequest = new SignupRequest(
                "test_username",
                "test_password",
                "test_role",
                "test_firstname",
                "test_lastname"
        );

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                signupRequest.getRole(),
                signupRequest.getFirstname(),
                signupRequest.getLastname()
        );

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteById(signupRequest.getUsername());
    }

    @Test
    void shouldBeAbleToSignupAUser() throws Exception {
        String signupRequestJson = new ObjectMapper().writeValueAsString(signupRequest);
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequestJson)
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isCreated()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("CREATED")
        );
    }

    @Test
    void shouldBeAbleToLoginUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest(
                "test_username",
                "test_password"
        );
        String loginRequestJson = new ObjectMapper().writeValueAsString(loginRequest);
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson)
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
}