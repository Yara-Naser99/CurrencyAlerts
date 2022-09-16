package com.example.currencyalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
public class CurrencyAlertsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyAlertsApplication.class, args);
    }

}
