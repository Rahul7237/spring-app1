package com.example.Overflow.controller;


import com.example.Overflow.jwt.JwtUtil;
import com.example.Overflow.model.AuthenticationRequest;
import com.example.Overflow.model.AuthenticationResponse;
import com.example.Overflow.model.CustomUserDetails;
import com.example.Overflow.model.User;
import com.example.Overflow.service.MyUserDetailsService;
import com.example.Overflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.NoSuchElementException;

import static com.example.Overflow.util.Util.encrypt;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());


        final String jwt = jwtTokenUtil.generateToken(userDetails);

         User user = userService.findUserByEmail(userDetails.getUsername());


        return ResponseEntity.ok(new AuthenticationResponse(jwt,user));
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@RequestBody User user) throws Exception {

        try{
            String originalString = user.getPassword();
            String encryptedString = encrypt(originalString);
            user.setPassword(encryptedString);
            System.out.println("password is " + originalString + "now " + encryptedString);
            Date createDate = new Date();
            user.setCreateDate(createDate);
            userService.saveUser(user);
            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.setUsername(user.getEmail());
            authenticationRequest.setPassword(originalString);
            return createAuthenticationToken(authenticationRequest);
        } catch (NoSuchElementException | MessagingException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

    }

}