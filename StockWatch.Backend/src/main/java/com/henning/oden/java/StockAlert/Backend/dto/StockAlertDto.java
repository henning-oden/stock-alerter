package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class StockAlertDto {
    String emailAddress;
    String username;
    String stockCommonName;
    BigDecimal lastPrice;
}
