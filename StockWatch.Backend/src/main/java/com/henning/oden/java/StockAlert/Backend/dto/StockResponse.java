package com.henning.oden.java.StockAlert.Backend.dto;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import lombok.Value;

import java.util.List;

@Value
public class StockResponse {
    private List<Stock> stocks;

    /**
     * Returns a brief description detailing how many stocks are in the list.
     */
    @Override
    public String toString() {
        return "A response containing a list of " + stocks.size() + " stocks.";
    }
}
