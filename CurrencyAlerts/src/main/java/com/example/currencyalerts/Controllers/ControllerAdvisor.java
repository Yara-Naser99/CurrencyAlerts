package com.example.currencyalerts.Controllers;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Services.CurrencyNotFoundException;
import com.example.currencyalerts.Services.UserNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<User> handleUserNotFound(UserNotFoundException exc, WebRequest req) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<Currency> handleCurrencyNotFound(CurrencyNotFoundException exc, WebRequest req) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}