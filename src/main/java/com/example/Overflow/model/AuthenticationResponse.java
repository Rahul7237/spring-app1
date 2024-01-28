package com.example.Overflow.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private  User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthenticationResponse(String jwt, User user) {
        this.user = user;
        this.jwt = jwt;
    }
    public AuthenticationResponse(String jwt) {

        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
