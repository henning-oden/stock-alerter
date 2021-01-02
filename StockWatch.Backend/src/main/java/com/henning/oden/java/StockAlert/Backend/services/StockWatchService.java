package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.StockWatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class StockWatchService {
    private StockWatchRepository stockWatches;

    public StockWatchService (StockWatchRepository watches) {
        stockWatches = watches;
    }

    public Collection<StockWatch> findStockWatchesByStock(Stock stock) {
        return findStockWatchesByStockId(stock.getId());
    }

    public Collection<StockWatch> findStockWatchesByStockId(long id) {
        Optional<Collection<StockWatch>> watchesOptional = stockWatches.findByStockId(id);
        return watchesOptional.orElse(new ArrayList<>());
    }

    public Collection<StockWatch> findStockWatchesByUser(SystemUser user) {
        return findStockWatchesByUserId(user.getId());
    }

    private Collection<StockWatch> findStockWatchesByUserId(long id) {
        Optional<Collection<StockWatch>> watchesOptional = stockWatches.findByUserId(id);
        return watchesOptional.orElse(new ArrayList<>());
    }

    public StockWatch saveStockWatch(StockWatch stockWatch) {
        return stockWatches.saveAndFlush(stockWatch);
    }

    public void deleteStockWatch(StockWatch stockWatch) {
        stockWatches.delete(stockWatch);
    }
}
