package com.henning.oden.java.StockAlert.Backend.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "stocks")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column
    @NotEmpty
    @Getter @Setter
    private String code;

    @Column
    @NotEmpty
    @Getter @Setter
    private String commonName;

    public Stock(String code, String commonName) {
        this.code = code;
        this.commonName = commonName;
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", commonName='" + commonName + '\'' +
                '}';
    }
}
