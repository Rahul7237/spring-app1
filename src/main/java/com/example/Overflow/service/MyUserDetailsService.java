package com.example.Overflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.Overflow.util.Util.decrypt;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            com.example.Overflow.model.User user = userService.findUserByEmail(s);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            String userName = user.getEmail();
            String password = decrypt(user.getPassword());
            return new User(userName, password, new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error loading user by username", e);
        }
    }
}
