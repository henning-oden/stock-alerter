package com.henning.oden.java.StockAlert.Backend.dto;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import lombok.Value;

import java.util.List;

@Value
public class StockResponse {
    private List<Stock> stocks;
}
