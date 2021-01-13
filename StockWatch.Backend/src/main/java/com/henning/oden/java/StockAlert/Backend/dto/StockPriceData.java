package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
public class StockPriceData {
    private BigDecimal price;
    private ZonedDateTime dateTime;
}
