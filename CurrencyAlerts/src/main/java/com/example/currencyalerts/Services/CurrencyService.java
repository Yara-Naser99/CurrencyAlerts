package com.example.currencyalerts.Services;

import com.example.currencyalerts.Exceptions.*;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Repositories.CurrencyRepository;
import com.example.currencyalerts.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.currencyalerts.Models.User.Role.ADMIN;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    @Autowired
    private UserRepository userRepository;

    private Set<String> unsupportedSet = new HashSet<>(Arrays.asList("ETH", "LTC", "ZKN", "MRD", "LPR", "GBZ"));
    public List<Currency> findAll() {
        return repository.findAll();
    }

    public void addCurrency(String symbol, String name, double currentPrice, int userId) throws UserUnauthorizedException, UnsupportedCurrencyCreationException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && user.get().getRole() == ADMIN && !unsupportedSet.contains(symbol)) {
            Currency currency = new Currency(symbol, name, currentPrice, true, LocalDateTime.now());
            repository.save(currency);
        } else if (user.isPresent() && user.get().getRole() != ADMIN) {
            throw new UserUnauthorizedException();
        } else if (unsupportedSet.contains(symbol)) {
            throw new UnsupportedCurrencyCreationException();
        }
    }

    public void deleteCurrencyById(int id, int userId) throws UserUnauthorizedException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && user.get().getRole() == ADMIN) {
            repository.deleteById(id);
        } else {
            throw new UserUnauthorizedException();
        }

    }

    /* TODO: debug the case when an exception is thrown */
    public void updateCurrency(int id, double currentPrice, int userId) throws CurrencyNotFoundException, UserUnauthorizedException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && user.get().getRole() == ADMIN) {
            Currency retrievedCurrency = repository.findById(id).get();
            if (retrievedCurrency != null) {
                retrievedCurrency.setCurrentPrice(currentPrice);
                repository.save(retrievedCurrency);
            } else {
                throw new CurrencyNotFoundException();
            }
        } else if (user.isPresent() && user.get().getRole() != ADMIN) {
            throw new UserUnauthorizedException();
        }
    }

    public Currency findCurrencyById(int id) throws CurrencyNotFoundException {

        Optional<Currency> oCurrency = repository.findById(id);

        if (!oCurrency.isPresent()) {
            throw new CurrencyNotFoundException();
        }

        return oCurrency.get();
    }
}