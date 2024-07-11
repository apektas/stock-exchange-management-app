package com.inghub.sems.impl;

import com.inghub.sems.entity.Stock;
import com.inghub.sems.entity.StockExchange;
import com.inghub.sems.model.StockCreateRequest;
import com.inghub.sems.model.StockPriceUpdateRequest;
import com.inghub.sems.repository.StockExchangeRepository;
import com.inghub.sems.repository.StockRepository;
import com.inghub.sems.service.impl.StockServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new stock")
    void createStock_ShouldCreateStock() {
        StockCreateRequest request = new StockCreateRequest();
        request.setName("AAPL");
        request.setDescription("Apple Inc.");
        request.setCurrentPrice(new BigDecimal("150.00"));

        Stock stock = new Stock();
        stock.setName("AAPL");
        stock.setDescription("Apple Inc.");
        stock.setCurrentPrice(new BigDecimal("150.00"));

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock createdStock = stockService.createStock(request);

        assertNotNull(createdStock);
        assertEquals("AAPL", createdStock.getName());
        assertEquals("Apple Inc.", createdStock.getDescription());
        assertEquals(new BigDecimal("150.00"), createdStock.getCurrentPrice());
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    @DisplayName("Should update stock price")
    void updateStockPrice_ShouldUpdatePrice() {
        StockPriceUpdateRequest request = new StockPriceUpdateRequest();
        request.setStockName("AAPL");
        request.setNewPrice(new BigDecimal("160.00"));

        Stock stock = new Stock();
        stock.setName("AAPL");
        stock.setCurrentPrice(new BigDecimal("150.00"));

        when(stockRepository.findByName("AAPL")).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock updatedStock = stockService.updateStockPrice(request);

        assertNotNull(updatedStock);
        assertEquals(new BigDecimal("160.00"), updatedStock.getCurrentPrice());
        verify(stockRepository, times(1)).findByName("AAPL");
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when updating price for non-existing stock")
    void updateStockPrice_StockNotFound_ShouldThrowException() {
        StockPriceUpdateRequest request = new StockPriceUpdateRequest();
        request.setStockName("AAPL");
        request.setNewPrice(new BigDecimal("160.00"));

        when(stockRepository.findByName("AAPL")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> stockService.updateStockPrice(request));
        verify(stockRepository, times(1)).findByName("AAPL");
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("Should delete stock by name and update stock exchanges")
    void deleteStockByName_ShouldDeleteStockAndUpdateExchanges() {
        Stock stock = new Stock();
        stock.setName("AAPL");

        StockExchange stockExchange1 = new StockExchange();
        stockExchange1.setName("NYSE");
        stockExchange1.setStocks(new HashSet<>(Set.of(stock)));

        StockExchange stockExchange2 = new StockExchange();
        stockExchange2.setName("NASDAQ");
        stockExchange2.setStocks(new HashSet<>(Set.of(stock)));

        stock.setStockExchanges(new HashSet<>(Set.of(stockExchange1, stockExchange2)));

        when(stockRepository.findByName("AAPL")).thenReturn(Optional.of(stock));

        stockService.deleteStockByName("AAPL");

        verify(stockRepository, times(1)).findByName("AAPL");
        verify(stockExchangeRepository, times(1)).saveAll(stock.getStockExchanges());
        verify(stockRepository, times(1)).delete(stock);

        assertFalse(stockExchange1.getStocks().contains(stock));
        assertFalse(stockExchange2.getStocks().contains(stock));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existing stock")
    void deleteStockByName_StockNotFound_ShouldThrowException() {
        when(stockRepository.findByName("AAPL")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> stockService.deleteStockByName("AAPL"));
        verify(stockRepository, times(1)).findByName("AAPL");
        verify(stockExchangeRepository, never()).saveAll(any());
        verify(stockRepository, never()).delete(any(Stock.class));
    }
}
