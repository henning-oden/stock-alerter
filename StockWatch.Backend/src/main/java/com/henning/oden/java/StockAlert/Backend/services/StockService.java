package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.repos.StockRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private StockRepository stocks;

    public StockService(StockRepository stockRepository) {
        stocks = stockRepository;
    }

    public Stock findStockByCode(String code) throws NotFoundException {
        return stocks.findByCode(code).orElseThrow(() -> new NotFoundException("Stock with code " + code + " not found"));
    }

    public Stock findStockByCommonName(String commonName) throws NotFoundException {
        return stocks.findByCommonName(commonName).orElseThrow(() -> new NotFoundException("Stock with common name " + commonName + " not found"));
    }

    public Stock saveStock(Stock stock) {
        Stock savedStock = stocks.saveAndFlush(stock);
        return savedStock;
    }

    public void deleteStock(Stock stock) {
        stocks.delete(stock);
    }
}
