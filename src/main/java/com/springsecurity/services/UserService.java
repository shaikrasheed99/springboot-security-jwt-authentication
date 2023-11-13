package com.springsecurity.services;

import com.springsecurity.helpers.request.SignupRequest;
import com.springsecurity.models.User;
import com.springsecurity.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    public void addUser(SignupRequest signupRequest) {
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                signupRequest.getFirstname(),
                signupRequest.getLastname(),
                signupRequest.getRole()
        );

        userRepository.save(user);
    }

    public User getUser(String username) throws Exception {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new Exception("user not found");
        }

        return user.get();
    }
}
