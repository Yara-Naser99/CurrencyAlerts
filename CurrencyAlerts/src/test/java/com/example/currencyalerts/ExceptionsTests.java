package com.example.currencyalerts;

import com.example.currencyalerts.Controllers.CurrencyController;
import com.example.currencyalerts.Exceptions.*;
import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Repositories.CurrencyRepository;
import com.example.currencyalerts.Repositories.UserRepository;
import com.example.currencyalerts.Services.AlertService;
import com.example.currencyalerts.Services.CurrencyService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
class ExceptionsTests {

    @InjectMocks
    private AlertService alertServiceMock;

    @InjectMocks
    private CurrencyService currencyServiceMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private CurrencyRepository currencyRepositoryMock;

    @Before
    public void setUp() {

    }

    @Test
    void injectedComponentsAreNotNull(){
        assertNotNull(alertServiceMock);
        assertNotNull(currencyServiceMock);
        assertNotNull(userRepositoryMock);
        assertNotNull(currencyRepositoryMock);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void addAlertOnDisabledCurrency() {
        //arrange
        Currency currency = new Currency();
        currency.setId(70);
        currency.setEnabled(false);
        when(currencyRepositoryMock.findById(70)).thenReturn(Optional.of(currency));
        //act+assert
        assertThrows(CurrencyIsDisabledException.class, () -> alertServiceMock.addAlert(70, 70,20));
    }

    @Test
    void addAlertOnNonExistentCurrency() {
        //arrange
        when(currencyRepositoryMock.findById(70)).thenReturn(Optional.empty());
        //act+assert
        assertThrows(CurrencyNotFoundException.class, () -> alertServiceMock.addAlert(70, 70,20));
    }

    @Test
    void addAlertWithNonExistentUser() {
        //arrange
        Currency currency = new Currency();
        currency.setEnabled(true);
        currency.setId(70);
        when(currencyRepositoryMock.findById(70)).thenReturn(Optional.of(currency));
        when(userRepositoryMock.findById(70)).thenReturn(Optional.empty());
        //act+assert
        assertThrows(UserNotFoundException.class, () -> alertServiceMock.addAlert(70, 70,20));
    }

    @Test
    void normalUserCannotAddCurrency() {
        User user = new User();
        user.setRole(User.Role.USER);
        when(userRepositoryMock.findById(70)).thenReturn(Optional.of(user));
        assertThrows(UserUnauthorizedException.class, () -> currencyServiceMock.addCurrency("symbol", "name", 100, 70));
    }

    @Test
    void adminCannotAddForbiddenCurrency() {
        User user = new User();
        user.setRole(User.Role.ADMIN);
        when(userRepositoryMock.findById(70)).thenReturn(Optional.of(user));
        assertThrows(UnsupportedCurrencyCreationException.class, () -> currencyServiceMock.addCurrency("ETH", "name", 100, 70));
    }

}
