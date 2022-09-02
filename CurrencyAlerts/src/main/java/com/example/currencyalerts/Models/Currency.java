package com.example.currencyalerts.Models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
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
    private LocalDateTime createdTime;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
    private Set<Alert> alerts = new HashSet<>();

    public Currency(String symbol, String name, double currentPrice, boolean enabled, LocalDateTime createdTime) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.enabled = enabled;
        this.createdTime = createdTime;
    }

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
