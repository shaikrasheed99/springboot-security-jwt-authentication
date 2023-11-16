package com.springsecurity.controllers;

import com.springsecurity.dto.response.ErrorResponse;
import com.springsecurity.dto.response.SuccessResponse;
import com.springsecurity.models.User;
import com.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> user(@PathVariable String username, Principal principal) throws Exception {
        if (!username.equals(principal.getName())) {
            String errorMessage = "user cannot access another user details";
            String response = new ErrorResponse()
                    .status("error")
                    .code(HttpStatus.FORBIDDEN)
                    .message(errorMessage)
                    .convertToJson();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        User user = userService.getUser(username);

        HashMap<Object, Object> data = new HashMap<>();
        data.put("user", user);
        String message = "fetched user details successfully!!";

        String response = new SuccessResponse()
                .status("success")
                .code(HttpStatus.OK)
                .message(message)
                .data(data)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<?> users(Principal principal) throws Exception {
        List<User> users = userService.getAllUsers();
        HashMap<Object, Object> data = new HashMap<>();
        data.put("users", users);
        String message = "fetched users details successfully!!";

        String response = new SuccessResponse()
                .status("success")
                .code(HttpStatus.OK)
                .message(message)
                .data(data)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
