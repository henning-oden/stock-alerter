package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class ValidationResponse {
    private String result;
    private String message;
}
