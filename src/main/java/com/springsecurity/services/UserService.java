package com.springsecurity.services;

import com.springsecurity.dto.request.SignupRequest;
import com.springsecurity.models.User;
import com.springsecurity.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void addUser(SignupRequest signupRequest) {
        User user = new User(
                signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
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

    public List<User> getAllUsers() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .toList();
    }
}
