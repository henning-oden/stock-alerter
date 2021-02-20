package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class StockWatchDto {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private long userId;
    @Getter @Setter
    private long stockId;
    @Getter @Setter
    private String stockCode;
    @Getter @Setter
    private double minPrice;
    @Getter @Setter
    private double maxPrice;
    @Getter @Setter
    private int timesExceeded;
    @Getter @Setter
    private int alertThreshold;

/**
 * Auto-generated toString() method. Format is unspecified and may change.
 *
 */
    @Override
    public String toString() {
        return "StockWatchDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", stockId=" + stockId +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", timesExceeded=" + timesExceeded +
                ", alertThreshold=" + alertThreshold +
                '}';
    }
}
