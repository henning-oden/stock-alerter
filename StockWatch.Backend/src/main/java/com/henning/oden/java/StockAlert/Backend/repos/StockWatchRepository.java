package com.henning.oden.java.StockAlert.Backend.repos;

import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StockWatchRepository extends JpaRepository<StockWatch, Long> {
    List<StockWatch> findByStockId(long stockId);
    List<StockWatch> findByUserId(long userId);
}
