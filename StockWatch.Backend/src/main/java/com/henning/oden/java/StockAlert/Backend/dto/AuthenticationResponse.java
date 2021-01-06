package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class AuthenticationResponse {
    private String username, token;
}
