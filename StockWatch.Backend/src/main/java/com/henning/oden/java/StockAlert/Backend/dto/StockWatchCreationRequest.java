package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class StockWatchCreationRequest {
    private String stockCode;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int alertThreshold;

/**
 * Auto-generated toString() method. Format is unspecified and may change.
 *
 * Example: StockWatchCreationRequest{stockCode='FOO',minPrice=42,maxPrice=43,alertThreshold=5}
 */
    @Override
    public String toString() {
        return "StockWatchCreationRequest{" +
                "stockCode='" + stockCode + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", alertThreshold=" + alertThreshold +
                '}';
    }
}