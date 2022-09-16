package com.example.currencyalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Repositories.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.time.LocalDateTime;
import java.util.*;

@DirtiesContext
@SpringBootTest
class CurrencyRepositoryTests {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testFindById() {
        Currency currency = getCurrency();
        currencyRepository.save(currency);
        Optional<Currency> oResult = currencyRepository.findById(currency.getId());
        Currency result;
        if (oResult.isPresent()) {
            result = oResult.get();
            assertEquals(currency.getId(), result.getId());
        }
        cleanUp(currency);
    }

    @Test
    public void testFindAll() {
        Currency currency = getCurrency();
        currencyRepository.save(currency);
        List<Currency> result = new ArrayList<>(currencyRepository.findAll());
        assertEquals(result.size(), currencyRepository.count());
        cleanUp(currency);
    }

    @Test
    public void testDeleteById() {
        Currency currency = getCurrency();
        currencyRepository.save(currency);
        currencyRepository.deleteById(currency.getId());
        Optional<Currency> oCurrency = currencyRepository.findById(currency.getId());
        assertEquals(oCurrency, Optional.empty());
    }

    private Currency getCurrency() {
        Currency currency = new Currency();
        currency.setName("MockCurrency");
        currency.setEnabled(true);
        currency.setSymbol("MK");
        currency.setCurrentPrice(0);
        currency.setCreatedTime(LocalDateTime.now());
        return currency;
    }

    private void cleanUp(Currency currency) {
        if (currencyRepository.findById(currency.getId()).isPresent()) {
            currencyRepository.deleteById(currency.getId());
        }
    }
}
