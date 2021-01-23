package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class StockAlertDto {
    String emailAddress;
    String username;
    String stockCommonName;
    BigDecimal lastPrice;

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     *
     * Example: StockAlertDto{emailAddress="foo@example.com",username="foo",stockCommonName="bar",lastPrice=42.29}
     */
    @Override
    public String toString() {
        return "StockAlertDto{" +
                "emailAddress='" + emailAddress + ',' +
                ", username='" + username + ',' +
                ", stockCommonName='" + stockCommonName + ',' +
                ", lastPrice=" + lastPrice +
                '}';
    }
}
