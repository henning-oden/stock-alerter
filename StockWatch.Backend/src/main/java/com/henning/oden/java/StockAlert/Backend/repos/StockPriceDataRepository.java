package com.henning.oden.java.StockAlert.Backend.repos;

import com.henning.oden.java.StockAlert.Backend.entities.StockPriceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceDataRepository extends JpaRepository<StockPriceData, Long> {
    public void deleteByStockId(long stockId);
}
