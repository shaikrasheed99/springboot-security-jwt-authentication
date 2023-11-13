package com.springsecurity.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springsecurity.helpers.request.LoginRequest;
import com.springsecurity.helpers.request.SignupRequest;
import com.springsecurity.helpers.response.SuccessResponse;
import com.springsecurity.models.User;
import com.springsecurity.services.JwtService;
import com.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    public UserService userService;

    @Autowired
    public JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        userService.addUser(signupRequest);

        String token = jwtService.generateToken(signupRequest.getUsername(), signupRequest.getRole());

        HashMap<Object, Object> data = new HashMap<>();
        data.put("token", token);

        String message = "user has created successfully";
        String response = new SuccessResponse()
                .status("success")
                .code(HttpStatus.CREATED)
                .message(message)
                .data(data)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        User user = userService.getUser(loginRequest.getUsername());

        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        HashMap<Object, Object> data = new HashMap<>();
        data.put("token", token);

        String message = "user has logged in successfully";
        String response = new SuccessResponse()
                .status("success")
                .code(HttpStatus.OK)
                .message(message)
                .data(data)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
