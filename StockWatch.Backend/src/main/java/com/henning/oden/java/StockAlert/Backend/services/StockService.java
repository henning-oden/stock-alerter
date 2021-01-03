package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.repos.StockRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock findStockByCode(String code) throws NotFoundException {
        return stockRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Stock with code " + code + " not found"));
    }

    public Stock findStockByCommonName(String commonName) throws NotFoundException {
        return stockRepository.findByCommonName(commonName).orElseThrow(() -> new NotFoundException("Stock with common name " + commonName + " not found"));
    }

    public Stock saveStock(Stock stock) {
        Stock savedStock = stockRepository.saveAndFlush(stock);
        return savedStock;
    }

    public void deleteStock(Stock stock) {
        stockRepository.delete(stock);
    }
}
