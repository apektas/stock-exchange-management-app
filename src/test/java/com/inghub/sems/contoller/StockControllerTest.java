package com.inghub.sems.contoller;

import com.inghub.sems.controller.StockController;
import com.inghub.sems.entity.Stock;
import com.inghub.sems.model.StockCreateRequest;
import com.inghub.sems.model.StockPriceUpdateRequest;
import com.inghub.sems.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    @DisplayName("Should create a new stock")
    void createStock_ShouldCreateStock() throws Exception {
        StockCreateRequest request = new StockCreateRequest();
        request.setName("AAPL");
        request.setDescription("Apple Inc.");
        request.setCurrentPrice(new BigDecimal("150.00"));

        Stock createdStock = new Stock();
        createdStock.setName("AAPL");
        createdStock.setDescription("Apple Inc.");
        createdStock.setCurrentPrice(new BigDecimal("150.00"));

        when(stockService.createStock(any(StockCreateRequest.class))).thenReturn(createdStock);

        mockMvc.perform(post("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"AAPL\",\"description\":\"Apple Inc.\",\"currentPrice\":150.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("AAPL"))
                .andExpect(jsonPath("$.description").value("Apple Inc."))
                .andExpect(jsonPath("$.currentPrice").value(150.00));

        verify(stockService, times(1)).createStock(any(StockCreateRequest.class));
    }

    @Test
    @DisplayName("Should update stock price")
    void updateCurrentPrice_ShouldUpdatePrice() throws Exception {
        StockPriceUpdateRequest request = new StockPriceUpdateRequest();
        request.setStockName("AAPL");
        request.setNewPrice(new BigDecimal("160.00"));

        Stock updatedStock = new Stock();
        updatedStock.setName("AAPL");
        updatedStock.setCurrentPrice(new BigDecimal("160.00"));

        when(stockService.updateStockPrice(any(StockPriceUpdateRequest.class))).thenReturn(updatedStock);

        mockMvc.perform(put("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stockName\":\"AAPL\",\"newPrice\":160.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("AAPL"))
                .andExpect(jsonPath("$.currentPrice").value(160.00));

        verify(stockService, times(1)).updateStockPrice(any(StockPriceUpdateRequest.class));
    }

    @Test
    @DisplayName("Should delete stock by name")
    void deleteStockByName_ShouldDeleteStock() throws Exception {
        doNothing().when(stockService).deleteStockByName("AAPL");

        mockMvc.perform(delete("/api/v1/stock/AAPL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(stockService, times(1)).deleteStockByName("AAPL");
    }
}
