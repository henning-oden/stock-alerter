package com.henning.oden.java.StockAlert.Backend.entities;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private static final long serializableVersionUID = -6545648414564L;
    private String username, password;

    public AuthenticationRequest() {

    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


