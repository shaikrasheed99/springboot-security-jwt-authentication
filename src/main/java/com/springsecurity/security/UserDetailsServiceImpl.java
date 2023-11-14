package com.springsecurity.security;

import com.springsecurity.models.User;
import com.springsecurity.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with this username"));

        return new UserDetailsImpl(user);
    }
}
