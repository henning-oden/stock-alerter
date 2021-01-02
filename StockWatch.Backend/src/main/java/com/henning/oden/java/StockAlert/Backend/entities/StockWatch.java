package com.henning.oden.java.StockAlert.Backend.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stock_watches")
public class StockWatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long userId;

    @Column
    private long stockId;

    @Column
    private double minPrice;

    @Column
    private double maxPrice;

    @Column
    private int timesExceeded;

    @Column
    private int alertThreshold;

    @JsonInclude()
    @Transient
    private Stock stock;

    @JsonInclude()
    @Transient
    private SystemUser user;

    public StockWatch() {
    }

    public StockWatch(long userId, long stockId, double minPrice, double maxPrice, int countExceeded, int alertThreshold) {
        this.userId = userId;
        this.stockId = stockId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.timesExceeded = countExceeded < 1 ? 5 : countExceeded;
        this.alertThreshold = alertThreshold < 1 ? 5 : alertThreshold;
    }

    public StockWatch(double minPrice, double maxPrice, int countExceeded, int alertThreshold, Stock stock, SystemUser user) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.timesExceeded = countExceeded;
        this.alertThreshold = alertThreshold;
        this.stock = stock;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getTimesExceeded() {
        return timesExceeded;
    }

    public void setTimesExceeded(int timesExceeded) {
        this.timesExceeded = timesExceeded;
    }

    public int getAlertThreshold() {
        return alertThreshold;
    }

    public void setAlertThreshold(int alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    public void setUserId(long userId) { this.userId = userId; }

    public void setStockId(long stockId) { this.stockId = stockId; }

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

    public SystemUser getUser() { return user; }

    public void setUser(SystemUser user) { this.user = user; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockWatch that = (StockWatch) o;
        return id == that.id && userId == that.userId && stockId == that.stockId && timesExceeded == that.timesExceeded && alertThreshold == that.alertThreshold;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, stockId, minPrice, maxPrice, timesExceeded, alertThreshold);
    }
}
