package com.henning.oden.java.StockAlert.Backend.entities;

import com.henning.oden.java.StockAlert.Backend.dto.StockWatchCreationRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_watches")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StockWatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;

    @Column
    @Getter @Setter
    private long userId;

    @Column
    @Getter @Setter
    private long stockId;

    @Column
    @Getter @Setter
    private BigDecimal minPrice;

    @Column
    @Getter @Setter
    private BigDecimal maxPrice;

    @Column
    @Getter @Setter
    private int timesExceeded;

    @Column
    @Getter @Setter
    private int alertThreshold;

    public StockWatch(long userId, long stockId, BigDecimal minPrice, BigDecimal maxPrice, int alertThreshold) {
        this.userId = userId;
        this.stockId = stockId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.alertThreshold = alertThreshold < 1 ? 5 : alertThreshold;
    }

    public static class Builder {
        private long userId;
        private long stockId;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private int alertThreshold;

        public Builder(long userId) {
            this.userId = userId;
        }

        public Builder forStockId(long stockId) {
            this.stockId = stockId;
            return this;
        }

        public Builder withMinPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Builder withMaxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public Builder withAlertThreshold(int alertThreshold) {
            this.alertThreshold = alertThreshold;
            return this;
        }

        public StockWatch build() {
            StockWatch stockWatch = new StockWatch();

            stockWatch.userId = userId;
            stockWatch.stockId = stockId;
            stockWatch.minPrice = minPrice;
            stockWatch.maxPrice = maxPrice;
            stockWatch.alertThreshold = alertThreshold;
            return stockWatch;
        }
    }
}
