package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class RegistrationResponse {
    private String result;
    private String message;

    /**
     * Returns a summary of the response to the registration request
     * readable by humans.
     *
     * Example: "Success; New user with username foo registered. Please check your e-mail!"
     */
    @Override
    public String toString() {
        return result + "; " + message;
    }
}
