package com.example.currencyalerts;

import static com.example.currencyalerts.Models.Alert.Status.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.currencyalerts.Models.*;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.*;

@DirtiesContext
@SpringBootTest
class AlertRepositoryTests {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testFindById() {
        Alert alert = getAlert();
        alertRepository.save(alert);
        Optional<Alert> oResult = alertRepository.findById(alert.getId());
        Alert result;
        if (oResult.isPresent()) {
            result = oResult.get();
            assertEquals(alert.getId(), result.getId());
        }
        cleanUp(alert, alert.getUser(), alert.getCurrency());
    }

    @Test
    public void testFindAll() {
        Alert alert = getAlert();
        alertRepository.save(alert);
        List<Alert> result = new ArrayList<>(alertRepository.findAll());
        assertEquals(result.size(), alertRepository.count());
        cleanUp(alert, alert.getUser(), alert.getCurrency());
    }

    @Test
    public void testDeleteById() {
        Alert alert = getAlert();
        alertRepository.save(alert);
        alertRepository.deleteById(alert.getId());
        Optional<Alert> oAlert = alertRepository.findById(alert.getId());
        assertEquals(oAlert, Optional.empty());
        cleanUp(alert.getUser(), alert.getCurrency());
    }

    private Alert getAlert() {
        Alert alert = new Alert(getAndSaveUser(), getAndSaveCurrency(), 0, NEW, LocalDateTime.now());
        return alert;
    }

    private User getAndSaveUser() {
        User user = new User(User.Role.USER, "userName", "firstName", "lastName", "email", "phoneNumber");
        userRepository.save(user);
        return user;
    }

    private Currency getAndSaveCurrency() {
        Currency currency = new Currency();
        currency.setName("MockCurrency");
        currency.setEnabled(true);
        currency.setSymbol("MK");
        currency.setCurrentPrice(0);
        currency.setCreatedTime(LocalDateTime.now());
        currencyRepository.save(currency);
        return currency;
    }

    private void cleanUp(Alert alert, User user, Currency currency) {
        if (alertRepository.findById(alert.getId()).isPresent()) {
            alertRepository.deleteById(alert.getId());
        }
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.deleteById(user.getId());
        }
        if (currencyRepository.findById(currency.getId()).isPresent()) {
            currencyRepository.deleteById(currency.getId());
        }
    }

    private void cleanUp(User user, Currency currency) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.deleteById(user.getId());
        }
        if (currencyRepository.findById(currency.getId()).isPresent()) {
            currencyRepository.deleteById(currency.getId());
        }
    }
}
