package com.inghub.sems.impl;

import com.inghub.sems.entity.Stock;
import com.inghub.sems.entity.StockExchange;
import com.inghub.sems.exception.EntityAlreadyExistsException;
import com.inghub.sems.repository.StockExchangeRepository;
import com.inghub.sems.repository.StockRepository;
import com.inghub.sems.service.impl.StockExchangeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockExchangeServiceImplTest {

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockExchangeServiceImpl stockExchangeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return stocks when stock exchange exists")
    void getStocksByStockExchange_StockExchangeExists_ReturnsStocks() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("NYSE");


        Stock stock = new Stock();
        stock.setName("AAPL");

        stockExchange.setStocks(Set.of(stock));

        when(stockExchangeRepository.findByName("NYSE")).thenReturn(Optional.of(stockExchange));

        var stocks = stockExchangeService.getStocksByStockExchange("NYSE");

        assertNotNull(stocks);
        assertEquals(1, stocks.size());
        verify(stockExchangeRepository, times(1)).findByName("NYSE");
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when stock is not found")
    void getStocksByStockExchange_StockExchangeDoesNotExist_ThrowsException() {
        when(stockExchangeRepository.findByName("NYSE")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> stockExchangeService.getStocksByStockExchange("NYSE"));
    }

    @Test
    @DisplayName("Should add stock to exchange when both stock and exchange exist")
    void addStockToExchange_StockAndExchangeExist_AddsStock() {
        Stock stock = new Stock();
        stock.setName("AAPL");

        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("NYSE");
        stockExchange.setStocks(new HashSet<>()); // Use a mutable set

        when(stockRepository.findByName("AAPL")).thenReturn(Optional.of(stock));
        when(stockExchangeRepository.findByName("NYSE")).thenReturn(Optional.of(stockExchange));

        stockExchangeService.addStockToExchange("NYSE", "AAPL");

        verify(stockRepository, times(1)).findByName("AAPL");
        verify(stockExchangeRepository, times(1)).findByName("NYSE");
        verify(stockExchangeRepository, times(1)).save(stockExchange);
        assertTrue(stockExchange.getStocks().contains(stock));
    }

    @Test
    @DisplayName("Should throw EntityAlreadyExistsException when stock already in exchange")
    void addStockToExchange_StockAlreadyInExchange_ThrowsException() {
        Stock stock = new Stock();
        stock.setName("AAPL");

        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("NYSE");
        stockExchange.setStocks(Set.of(stock));

        when(stockRepository.findByName("AAPL")).thenReturn(Optional.of(stock));
        when(stockExchangeRepository.findByName("NYSE")).thenReturn(Optional.of(stockExchange));

        assertThrows(EntityAlreadyExistsException.class, () -> stockExchangeService.addStockToExchange("NYSE", "AAPL"));
    }

    @Test
    @DisplayName("Should remove stock from exchange when both stock and exchange exist")
    void removeStockFromExchange_StockAndExchangeExist_RemovesStock() {
        Stock stock = new Stock();
        stock.setName("AAPL");

        Set<Stock> stocks = new HashSet<>();
        stocks.add(stock);
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("NYSE");
        stockExchange.setStocks(stocks); // Use a mutable set

        when(stockRepository.findByName("AAPL")).thenReturn(Optional.of(stock));
        when(stockExchangeRepository.findByName("NYSE")).thenReturn(Optional.of(stockExchange));

        stockExchangeService.removeStockFromExchange("NYSE", "AAPL");

        verify(stockRepository, times(1)).findByName("AAPL");
        verify(stockExchangeRepository, times(1)).findByName("NYSE");
        verify(stockExchangeRepository, times(1)).save(stockExchange);
        assertFalse(stockExchange.getStocks().contains(stock));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when stock not in exchange")
    void removeStockFromExchange_StockNotInExchange_ThrowsException() {
        Stock stock = new Stock();
        stock.setName("AAPL");

        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("NYSE");
        stockExchange.setStocks(Collections.emptySet());

        when(stockRepository.findByName("AAPL")).thenReturn(Optional.of(stock));
        when(stockExchangeRepository.findByName("NYSE")).thenReturn(Optional.of(stockExchange));

        assertThrows(EntityNotFoundException.class, () -> stockExchangeService.removeStockFromExchange("NYSE", "AAPL"));
    }
}
