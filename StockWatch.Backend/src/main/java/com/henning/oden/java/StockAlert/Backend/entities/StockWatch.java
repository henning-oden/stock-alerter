package com.henning.oden.java.StockAlert.Backend.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
    private double minPrice;

    @Column
    @Getter @Setter
    private double maxPrice;

    @Column
    @Getter @Setter
    private int timesExceeded;

    @Column
    @Getter @Setter
    private int alertThreshold;

    @JsonInclude()
    @Transient
    @Getter @Setter
    private Stock stock;

    @JsonInclude()
    @Transient
    @Getter @Setter
    private SystemUser user;

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
}
