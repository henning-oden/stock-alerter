package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.repos.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public List<Stock> findAll() { return stockRepository.findAll(); }

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

    public Optional<Stock> findById(long id) {
        return stockRepository.findById(id);
    }

    public void deleteStock(Stock stock) {
        stockRepository.delete(stock);
    }

    public List<Stock> findStockByCodeContainingOrCommonNameContainingAllIgnoreCase(String code, String commonName) {
        return stockRepository.findByCodeContainingOrCommonNameContainingAllIgnoreCase(code, commonName);
    }

    public List<Stock> findByCodeContainingIgnoreCase(String codePart) {
        return stockRepository.findByCodeContainingIgnoreCase(codePart);
    }
    public List<Stock> findByCommonNameContainingIgnoreCase(String commonNamePart) {
        return  stockRepository.findByCommonNameContainingIgnoreCase(commonNamePart);
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "StockService{" +
                "stockRepository=" + stockRepository +
                '}';
    }
}
