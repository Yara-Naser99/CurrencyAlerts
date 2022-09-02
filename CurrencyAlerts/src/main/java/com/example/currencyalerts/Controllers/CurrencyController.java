package com.example.currencyalerts.Controllers;

import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Services.CurrencyService;
import com.example.currencyalerts.Services.UnsupportedCurrencyCreationException;
import com.example.currencyalerts.Services.UserUnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class CurrencyController{
    @Autowired
    private CurrencyService service;

    @GetMapping("/currencies")
    public List<Currency> findAll() {
        return service.findAll();
    }

    @GetMapping("/currencies/{id}")
    public Currency findCurrencyById(@PathVariable(value="id") int id) throws Exception {
        return service.findCurrencyById(id);
    }

    @PostMapping("currencies")
    public void addCurrency(@RequestParam(name = "symbol") String symbol, @RequestParam(name = "name") String name,
                                @RequestParam(name = "currentPrice") double currentPrice, @RequestParam(name = "userId") int userId) throws UserUnauthorizedException, UnsupportedCurrencyCreationException {
        service.addCurrency(symbol, name, currentPrice, userId);
    }

    @DeleteMapping("currencies/{id}")
    public void deleteCurrency(@PathVariable (value="id") int id, @RequestParam(name = "userId") int userId) throws UserUnauthorizedException {
        service.deleteCurrencyById(id, userId);
    }

    @PutMapping("currencies/{id}")
    public void updateCurrency(@PathVariable (value="id") int id, @RequestParam (name = "currentPrice") double currentPrice, @RequestParam(name = "userId") int userId) throws Exception {
        service.updateCurrency(id, currentPrice, userId);
    }

}