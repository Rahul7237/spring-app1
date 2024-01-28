package com.example.Overflow.controller;

import com.example.Overflow.model.User;
import com.example.Overflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.util.*;

import static com.example.Overflow.util.Util.encrypt;

@RestController
@RequestMapping("/users")
public class UserController {



    @Autowired
    UserService userService;

    @GetMapping("/secure")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> secureEndpoint() {
        return ResponseEntity.ok("This is a secure endpoint!");
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public endpoint!");
    }
    @GetMapping("")
    public List<User> list() {
        System.out.println("getting all users");
        return userService.listAllUser();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
          //  user.setPassword(decrypt(user.getPassword()));
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public ResponseEntity<User> add(@RequestBody User user) {
        try{
        String originalString = user.getPassword();
        String encryptedString = encrypt(originalString);
        user.setPassword(encryptedString);
        System.out.println("password is " + originalString + "now " + encryptedString);
        Date createDate = new Date();
        user.setCreateDate(createDate);
        userService.saveUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException | MessagingException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id) {
        try {
            User existUser = userService.getUser(id);
            user.setId(id);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException | MessagingException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        userService.deleteUser(id);
    }




}
