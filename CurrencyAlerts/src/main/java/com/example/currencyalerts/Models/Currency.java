package com.example.currencyalerts.Models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "currency")
public class Currency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "current_price")
    private double currentPrice;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                ", createdTime=" + createdTime +
                ", enabled=" + enabled +
                '}';
    }
}
