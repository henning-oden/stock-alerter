package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.repos.StockRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Optional<Stock> findStockByCode(String code) {
        return stockRepository.findByCode(code);
    }

    public Optional<Stock> findStockByCommonName(String commonName) {
        return stockRepository.findByCommonName(commonName);
    }

    public Stock saveStock(Stock stock) {
        Stock savedStock = stockRepository.saveAndFlush(stock);
        return savedStock;
    }

    public void deleteStock(Stock stock) {
        stockRepository.delete(stock);
    }
}
