package com.example.currencyalerts.Controllers;

import com.example.currencyalerts.Models.Currency;
import com.example.currencyalerts.Services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private UserService userService;

    @Test
    public void getAllCurrencies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currencies"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCurrencyById() throws Exception {
        Currency currency = new Currency("symbol", "name", 0, true);
        currency.setId(1);

        when(currencyService.findCurrencyById(currency.getId())).thenReturn(currency);

        mockMvc.perform(MockMvcRequestBuilders.get("/currencies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value("symbol"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enabled").value(true));
    }

    @Test
    public void addCurrency() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/currencies")
                        .param("symbol", "symbol")
                        .param("name", "name")
                        .param("currentPrice", "0")
                        .param("enabled", "true")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCurrency() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/currencies/{id}", 1)
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployeeAPI() throws Exception
    {
        Currency currency = new Currency("symbol", "name", 0, true);
        currency.setId(1);

        when(currencyService.findCurrencyById(currency.getId())).thenReturn(currency);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/currencies/{id}", 1)
                        .param("currentPrice", "1")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

}
