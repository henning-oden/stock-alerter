package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class StockWatchCreationRequest {
    private String stockCode;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int alertThreshold;
}