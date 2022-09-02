package com.example.currencyalerts.Services;

import com.example.currencyalerts.Models.Alert;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.currencyalerts.Models.Alert.Status.*;

@Service
public class AlertService {
    
    @Autowired
    private AlertRepository repository;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserService userService;
    public List<Alert> findAll() {
        return repository.findAll();
    }

    public Alert findAlertById(int id) {
        return repository.findById(id).get();
    }

    public Alert addAlert(int userId, int currencyId, double targetPrice) throws UserNotFoundException, CurrencyNotFoundException, CurrencyIsDisabledException {
        Currency currency = currencyService.findCurrencyById(currencyId);
        if (currency.isEnabled()) {
            Alert alert = new Alert();
            alert.setCreatedAt(LocalDateTime.now());
            alert.setStatus(NEW);
            alert.setTargetPrice(targetPrice);
            currency.getAlerts().add(alert);
            alert.setCurrency(currency);
            User user = userService.findUserById(userId);
            alert.setUser(user);
            user.getAlerts().add(alert);
            return repository.save(alert);
        } else {
            throw new CurrencyIsDisabledException();
        }
    }

    public void deleteAlertById(int id){
        repository.deleteById(id);
    }

    /* TODO: refactor after deciding on whether we should create a new alert post updating a non existent alert */
    public void updateAlert(Alert.Status status, Alert alert) {
        alert.setStatus(status);
        repository.save(alert);
    }

    /* TODO: set to 30 seconds */
    @Scheduled(fixedDelay = 1000)
    public void triggerAlert() {
        List<Alert> alerts = findAll();

        if (!alerts.isEmpty()) {
            for (Alert alert : alerts) {
                if (alert.getStatus() != CANCELLED) {
                    if (alert.getCurrency().getCurrentPrice() == alert.getTargetPrice() && alert.getStatus() == NEW) {
                        System.out.println("Hey " + alert.getUser().getUserName() + ", " + alert.getCurrency().getName() + " price just hit " + alert.getTargetPrice() + "!!!");
                        updateAlert(TRIGGERED, alert);
                    }
                    if (alert.getStatus() == TRIGGERED) {
                        updateAlert(ACKED, alert);
                        System.out.println("acked");/*TODO randomize acknowledgment*/
                    }
                }
            }
        }
    }

    public void ackAlert() {
        List<Alert> alerts = findAll();
        for (Alert alert : alerts) {
            if (alert.getStatus() == TRIGGERED) {
                alert.setStatus(ACKED);
                System.out.println("User " + alert.getUser().getUserName() + " acknowledged the alert");
            }
        }
    }

}
