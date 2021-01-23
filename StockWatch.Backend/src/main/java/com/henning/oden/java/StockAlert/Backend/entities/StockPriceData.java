package com.henning.oden.java.StockAlert.Backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "price_data")
@NoArgsConstructor
public class StockPriceData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;
    @Column
    @Getter @Setter
    private long stockId;
    @Column
    @Getter @Setter
    private BigDecimal price;
    @Basic
    @Getter @Setter
    private ZonedDateTime time;

    public StockPriceData(long stockId, BigDecimal price, ZonedDateTime time) {
        this.stockId = stockId;
        this.price = price;
        this.time = time;
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "StockPriceData{" +
                "id=" + id +
                ", stockId=" + stockId +
                ", price=" + price +
                ", time=" + time +
                '}';
    }
}
