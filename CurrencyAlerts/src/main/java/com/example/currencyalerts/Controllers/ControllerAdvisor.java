package com.example.currencyalerts.Controllers;
import com.example.currencyalerts.Exceptions.*;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Models.User;
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

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<Currency> handleUserUnauthorized(UserUnauthorizedException exc, WebRequest req) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CurrencyIsDisabledException.class)
    public ResponseEntity<Currency> handleCurrencyIsDisabled(CurrencyIsDisabledException exc, WebRequest req) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnsupportedCurrencyCreationException.class)
    public ResponseEntity<Currency> handleUnsupportedCurrency(UnsupportedCurrencyCreationException exc, WebRequest req) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}