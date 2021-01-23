package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;

    // Once again not certain what exactly to include here...
    @Override
    public String toString() {
        return "Registration request with username " + username;
    }
}
