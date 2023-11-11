package com.springsecurity.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springsecurity.helpers.request.SignupRequest;
import com.springsecurity.helpers.response.SuccessResponse;
import com.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    public UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        userService.addUser(signupRequest);

        String message = "user has created successfully";
        String response = new SuccessResponse()
                .status("success")
                .code(HttpStatus.CREATED)
                .message(message)
                .data(null)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
