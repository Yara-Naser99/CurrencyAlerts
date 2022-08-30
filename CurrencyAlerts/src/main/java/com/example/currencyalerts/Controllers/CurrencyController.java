package com.example.currencyalerts.Controllers;

import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Services.CurrencyNotFoundException;
import com.example.currencyalerts.Services.CurrencyService;
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
    public Currency addCurrency(@RequestBody Currency Currency){
        return service.addCurrency(Currency);
    }

    @DeleteMapping("currencies/{id}")
    public void deleteCurrency(@PathVariable (value="id") int id ){
        service.deleteCurrencyById(id);
    }

    @PutMapping("currencies/{id}")
    public Currency updateCurrency(@PathVariable (value="id") int id,@RequestBody Currency Currency) throws Exception {
        return service.updateCurrency(id,Currency);
    }

}