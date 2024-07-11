package com.inghub.sems.contoller;

import com.inghub.sems.controller.StockExchangeController;
import com.inghub.sems.entity.Stock;
import com.inghub.sems.service.StockExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StockExchangeControllerTest {

    @Mock
    private StockExchangeService stockExchangeService;

    @InjectMocks
    private StockExchangeController stockExchangeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stockExchangeController).build();
    }

    @Test
    @DisplayName("Should return all stocks for a given stock exchange")
    void getAllStocks_ShouldReturnStocks() throws Exception {
        Stock stock1 = new Stock();
        stock1.setName("AAPL");

        Stock stock2 = new Stock();
        stock2.setName("TSLA");

        when(stockExchangeService.getStocksByStockExchange(anyString())).thenReturn(List.of(stock1, stock2));

        mockMvc.perform(get("/api/v1/stock-exchange/NYSE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("AAPL"))
                .andExpect(jsonPath("$[1].name").value("TSLA"));

        verify(stockExchangeService, times(1)).getStocksByStockExchange("NYSE");
    }

    @Test
    @DisplayName("Should add stock to the stock exchange")
    void addStockToExchange_ShouldAddStock() throws Exception {
        doNothing().when(stockExchangeService).addStockToExchange(anyString(), anyString());

        mockMvc.perform(post("/api/v1/stock-exchange/NYSE/stock")
                        .param("stockName", "AAPL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(stockExchangeService, times(1)).addStockToExchange("NYSE", "AAPL");
    }

    @Test
    @DisplayName("Should remove stock from the stock exchange")
    void removeStockFromExchange_ShouldRemoveStock() throws Exception {
        doNothing().when(stockExchangeService).removeStockFromExchange(anyString(), anyString());

        mockMvc.perform(delete("/api/v1/stock-exchange/NYSE/stock")
                        .param("stockName", "AAPL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(stockExchangeService, times(1)).removeStockFromExchange("NYSE", "AAPL");
    }
}
