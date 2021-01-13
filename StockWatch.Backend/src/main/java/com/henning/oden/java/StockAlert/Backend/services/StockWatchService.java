package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.StockWatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockWatchService {
    private final StockWatchRepository stockWatches;

    public StockWatchService (StockWatchRepository watches) {
        stockWatches = watches;
    }

    public List<StockWatch> findStockWatchesByStock(Stock stock) {
        return findStockWatchesByStockId(stock.getId());
    }

    public List<StockWatch> findStockWatchesByStockId(long id) {
        List<StockWatch> watchesOptional = stockWatches.findByStockId(id);
        return watchesOptional;
    }

    public List<StockWatch> findStockWatchesByUser(SystemUser user) {
        return findStockWatchesByUserId(user.getId());
    }

    public List<StockWatch> findStockWatchesByUserId(long id) {
        List<StockWatch> watchesOptional = stockWatches.findByUserId(id);
        return watchesOptional;
    }

    public List<StockWatch> findAll() {
        return stockWatches.findAll();
    }

    public StockWatch saveStockWatch(StockWatch stockWatch) {
        return stockWatches.saveAndFlush(stockWatch);
    }

    public void deleteStockWatch(StockWatch stockWatch) {
        stockWatches.delete(stockWatch);
    }

    public Optional<StockWatch> findById(long id) {
        return stockWatches.findById(id);
    }
}
