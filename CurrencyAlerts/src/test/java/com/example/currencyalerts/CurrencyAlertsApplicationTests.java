package com.example.currencyalerts;

import com.example.currencyalerts.Controllers.CurrencyController;
import com.example.currencyalerts.Exceptions.UnsupportedCurrencyCreationException;
import com.example.currencyalerts.Exceptions.UserUnauthorizedException;
import com.example.currencyalerts.Models.User;
import com.example.currencyalerts.Repositories.UserRepository;
import com.example.currencyalerts.Services.CurrencyService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
class CurrencyAlertsApplicationTests {

    @InjectMocks
    private CurrencyService currencyServiceMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Test
    void contextLoads() {
    }

    @Test
    void normalUserCannotAddCurrency() {
        User user = new User();
        user.setRole(User.Role.USER);
        user.setId(70);
        when(userRepositoryMock.findById(70)).thenReturn(Optional.of(user));
        assertThrows(UserUnauthorizedException.class, () -> currencyServiceMock.addCurrency("symbol", "name", 100, 70));//String symbol, String name, double currentPrice, int userId)
    }

    @Test
    void adminCannotAddForbiddenCurrency() {
        User user = new User();
        user.setRole(User.Role.ADMIN);
        user.setId(70);
        when(userRepositoryMock.findById(70)).thenReturn(Optional.of(user));
        assertThrows(UnsupportedCurrencyCreationException.class, () -> currencyServiceMock.addCurrency("ETH", "name", 100, 70));//String symbol, String name, double currentPrice, int userId)
    }

}
