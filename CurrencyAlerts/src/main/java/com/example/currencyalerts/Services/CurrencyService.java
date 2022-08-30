package com.example.currencyalerts.Services;

import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    public List<Currency> findAll() {
        return repository.findAll();
    }

    public Currency addCurrency( Currency user){
        return repository.save(user);
    }

    public void deleteCurrencyById(int id){
        repository.deleteById(id);
    }

    /* TODO: debug the case when an exception is thrown */
    public Currency updateCurrency(int id, Currency currency) throws CurrencyNotFoundException{
        Currency retrievedCurrency =  repository.findById(id).get();
        if (retrievedCurrency != null) {
            retrievedCurrency.setCurrentPrice(currency.getCurrentPrice());
            return repository.save(retrievedCurrency);
        } else {
            throw new CurrencyNotFoundException();
        }
    }

    public Currency findCurrencyById(int id) throws CurrencyNotFoundException {

        Optional<Currency> oCurrency = repository.findById(id);

        if (oCurrency.isEmpty()) {
            throw new CurrencyNotFoundException();
        }

        throw new CurrencyNotFoundException();
    }
}