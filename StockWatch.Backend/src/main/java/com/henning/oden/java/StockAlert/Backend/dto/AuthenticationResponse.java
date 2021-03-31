package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class AuthenticationResponse {
    private String username, token;

    /**
     * provides a brief human-readable description of the AuthenticationResponse
     * including only the username.
     */
    // Similar to the case in AuthenticationRequest, does it make sense to exclude the token?
    @Override
    public String toString() {
        return "Authentication response for username " + username;
    }
}
