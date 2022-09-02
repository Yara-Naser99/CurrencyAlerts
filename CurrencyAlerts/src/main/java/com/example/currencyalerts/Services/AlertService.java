package com.example.currencyalerts.Services;

import com.example.currencyalerts.Exceptions.*;
import com.example.currencyalerts.Models.*;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
            User user = userService.findUserById(userId);
            Alert alert = new Alert(user, currency, targetPrice, NEW, LocalDateTime.now());
            currency.getAlerts().add(alert);
            user.getAlerts().add(alert);
            return repository.save(alert);
        } else {
            throw new CurrencyIsDisabledException();
        }
    }

    public void deleteAlertById(int id){
        repository.deleteById(id);
    }

    public void updateAlert(int id, Alert.Status status) {
        Optional<Alert> alert = repository.findById(id);
        if (alert.isPresent()) {
            alert.get().setStatus(status);
            repository.save(alert.get());
        }
    }

    /* Schedueled task runs each 30 seconds, acknowledgment is mimiced by a function that acknowledged alerts directly, it can be also randomized to be more real */
    @Scheduled(fixedDelay = 3000)
    public void triggerAlert() {
        List<Alert> alerts = findAll();

        if (!alerts.isEmpty()) {
            for (Alert alert : alerts) {
                if (alert.getStatus() != CANCELLED) {
                    if (alert.getCurrency().getCurrentPrice() == alert.getTargetPrice() && alert.getStatus() == NEW) {
                        System.out.println("Hey " + alert.getUser().getUserName() + ", " + alert.getCurrency().getName() + " price just hit " + alert.getTargetPrice() + "!!!");
                        updateAlert(alert.getId(), TRIGGERED);
                    }
                    if (alert.getStatus() == TRIGGERED) {
                        updateAlert(alert.getId(), ACKED);
                        System.out.println("Acked!");
                    }
                }
            }
        }
    }

}
