package com.springsecurity.services;

import com.springsecurity.helpers.request.SignupRequest;
import com.springsecurity.models.User;
import com.springsecurity.models.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    public UserRepository userRepository;

    @InjectMocks
    public UserService userService;

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
    }

    @Test
    void shouldAbleToSaveUserDetails() {
        when(userRepository.save(any(User.class))).thenReturn(null);

        userService.addUser(signupRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }
}