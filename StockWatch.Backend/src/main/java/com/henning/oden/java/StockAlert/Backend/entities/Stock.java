package com.henning.oden.java.StockAlert.Backend.entities;

import lombok.*;

import javax.persistence.*;
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
}
