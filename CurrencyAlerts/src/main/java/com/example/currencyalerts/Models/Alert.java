package com.example.currencyalerts.Models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "alert")
public class Alert implements Serializable {

    public enum Status {
        NEW,
        TRIGGERED,
        ACKED,
        CANCELLED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinTable(
            name = "user_alert",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    @ManyToOne
    @JoinTable(
            name = "currency_alert",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "currency_id"))
    private Currency currency;

    @Column(name = "target_price")
    private double targetPrice;

    @Enumerated
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Alert(User user, Currency currency, double targetPrice, Status status, LocalDateTime createdAt) {
        this.user = user;
        this.currency = currency;
        this.targetPrice = targetPrice;
        this.status = status;
        this.createdAt = createdAt;
    }
}
