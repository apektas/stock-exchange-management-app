package com.inghub.sems.service;

import com.inghub.sems.entity.Stock;
import jakarta.transaction.Transactional;

import java.util.List;

public interface StockExchangeService {

    List<Stock> getStocksByStockExchange(String exchangeName);

    void addStockToExchange(String exchangeName, String stockName);

    @Transactional
    void removeStockFromExchange(String exchangeName, String stockName);
}
