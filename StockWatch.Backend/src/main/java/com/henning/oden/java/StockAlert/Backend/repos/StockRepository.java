package com.henning.oden.java.StockAlert.Backend.repos;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByCode(String code);
    Optional<Stock> findByCommonName(String commonName);
    List<Stock> findByCodeContainingOrCommonNameContainingAllIgnoreCase(String codePart, String commonNamePart);
    List<Stock> findByCodeContainingIgnoreCase(String codePart);
    List<Stock> findByCommonNameContainingIgnoreCase(String commonNamePart);
}
