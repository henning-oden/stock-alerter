package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class ValidationResponse {
    private String result;
    private String message;

    /**
     * Provides a human-readable description of the validation response
     *
     * Exmaple: "Success; E-mail successfully validated. You may now log in."
     */
    @Override
    public String toString() {
        return result + "; " + message;
    }
}
