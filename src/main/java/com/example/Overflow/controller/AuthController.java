package com.example.Overflow.controller;


import com.example.Overflow.config.Mail;
import com.example.Overflow.jwt.JwtUtil;
import com.example.Overflow.model.AuthenticationRequest;
import com.example.Overflow.model.AuthenticationResponse;
import com.example.Overflow.model.ForgotRequest;
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
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

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
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist with this email");
        }

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        User user = userService.findUserByEmail(authenticationRequest.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist with this email");
        }

        return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
    }


    @PostMapping("/forgotPass")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotRequest forgotRequest) throws MessagingException {
        if (forgotRequest.getMode().equalsIgnoreCase("sendOtp")) {
            User user = userService.findUserByEmail(forgotRequest.getEmail());
            if (Objects.nonNull(user)) {
                String otp = generateOTP();
                user.setOtp(otp);
                // Send the OTP to the user through email or SMS
                userService.saveUser(user);
                Mail.SendOtpMail(user.getEmail(),otp);
                return ResponseEntity.ok().body("OTP sent successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist with this email");
            }
        } else if (forgotRequest.getMode().equalsIgnoreCase("verifyOtp")) {
            User user = userService.findUserByEmail(forgotRequest.getEmail());
            if (Objects.nonNull(user)) {
                if (user.getOtp().equalsIgnoreCase(forgotRequest.getOtp())) {
                    return ResponseEntity.ok().body("OTP verification successful");
                } else {
                    return ResponseEntity.badRequest().body("Invalid OTP");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist with this email");
            }
        } else if (forgotRequest.getMode().equalsIgnoreCase("updatePassword")) {
            User user = userService.findUserByEmail(forgotRequest.getEmail());
            if (Objects.nonNull(user)) {
                String encryptedString = encrypt(forgotRequest.getPassword());
                user.setPassword(encryptedString);
                userService.saveUser(user); // Save the updated user
                return ResponseEntity.ok().body("Password updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist with this email");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid mode");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        User userByEmail = null;
        try {
            userByEmail = userService.findUserByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userByEmail != null) {
            // User with provided email already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
        } else {
            try {
                String originalString = user.getPassword();
                String encryptedString = encrypt(originalString);
                user.setPassword(encryptedString);
                System.out.println("Password encrypted successfully.");

                Date createDate = new Date();
                user.setCreateDate(createDate);
                userService.saveUser(user);
                AuthenticationRequest authenticationRequest = new AuthenticationRequest();
                authenticationRequest.setUsername(user.getEmail());
                authenticationRequest.setPassword(originalString);
                return createAuthenticationToken(authenticationRequest);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create account.");
            }
        }
    }



    public static String generateOTP() {
        // Length of the OTP
        int length = 4;
        // Characters allowed in the OTP
        String charset = "0123456789";

        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        // Generate OTP by appending random characters from the charset
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charset.length());
            otp.append(charset.charAt(index));
        }

        return otp.toString();
    }
}
