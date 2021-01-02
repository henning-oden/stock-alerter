package com.henning.oden.java.StockAlert.Backend.repos;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface StockWatchRepository extends JpaRepository<StockWatch, Long> {
    Optional<Collection<StockWatch>> findByStockId(long stockId);
    Optional<Collection<StockWatch>> findByUserId(long userId);
}
