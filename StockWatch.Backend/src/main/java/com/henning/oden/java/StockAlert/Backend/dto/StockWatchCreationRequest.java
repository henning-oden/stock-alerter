package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

@Value
public class StockWatchCreationRequest {
    private String stockCode;
    private double minPrice;
    private double maxPrice;
    private int alertThreshold;
}