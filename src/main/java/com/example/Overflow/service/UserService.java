package com.example.Overflow.service;

import com.example.Overflow.config.Mail;
import com.example.Overflow.model.User;
import com.example.Overflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public List<User> listAllUser() {
        return userRepository.findAll();
    }

    public void saveUser(User user) throws MessagingException {
        userRepository.save(user);
        String lastName = "";
        String firstName = "";
        try{
            firstName = user.getfirstName();
            lastName = user.getlastName();
        }
        catch (Exception e){
            System.out.println("error while getting last name");
        }

        String name = firstName + " " + lastName;
        Mail.SendMail(user.getEmail(),name);
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User newUser) {
        Optional<User> optionalUser = userRepository.findById(newUser.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setfirstName(newUser.getfirstName());
            existingUser.setlastName(newUser.getlastName());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setMobile(newUser.getMobile());
            // Update any other fields as needed

            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with id " + newUser.getId() + " not found");
        }
    }
}


