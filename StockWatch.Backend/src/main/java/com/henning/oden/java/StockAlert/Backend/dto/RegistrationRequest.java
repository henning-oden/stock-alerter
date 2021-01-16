package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
}
