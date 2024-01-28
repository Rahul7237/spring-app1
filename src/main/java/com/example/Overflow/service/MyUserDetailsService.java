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
        com.example.Overflow.model.User user = null;
        try {
             user = userService.findUserByEmail(s);
            System.out.println("getting user");
             String userName = user.getEmail();
             String password = decrypt(user.getPassword());
            System.out.println(userName + password);
            return new User(userName, password, new ArrayList<>());
        }
        catch (Exception e){
            e.printStackTrace();

        }
            return  null;
    }
}
