package com.example.currencyalerts.Controllers;

import com.example.currencyalerts.Models.*;
import com.example.currencyalerts.Services.AlertService;
import com.example.currencyalerts.Services.CurrencyIsDisabledException;
import com.example.currencyalerts.Services.CurrencyNotFoundException;
import com.example.currencyalerts.Services.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AlertController{
    @Autowired
    private AlertService service;

    @GetMapping("/alerts")
    public List<Alert> findAll() {
        return service.findAll();
    }

    @GetMapping("/alerts/{id}")
    public Alert findAlertById(@PathVariable(value="id") int id) throws Exception {
        return service.findAlertById(id);
    }

    @PostMapping("alerts")
    public Alert addAlert(@RequestParam(name = "userId") int userId, @RequestParam(name = "currencyId") int currencyId, @RequestParam(name = "targetPrice") double targetPrice) throws UserNotFoundException, CurrencyNotFoundException, CurrencyIsDisabledException {
        return service.addAlert(userId, currencyId, targetPrice);
    }

    @DeleteMapping("alerts/{id}")
    public void deleteAlert(@PathVariable (value="id") int id ){
        service.deleteAlertById(id);
    }

    /*
    @PutMapping("alerts/{id}")
    public Alert updateAlert(@PathVariable (value="id") int id, @RequestBody Alert alert) throws Exception {
        return service.updateAlert(alert);
    }

     */

}