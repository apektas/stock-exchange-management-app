package com.inghub.sems.service;

import com.inghub.sems.entity.Stock;
import com.inghub.sems.model.StockCreateRequest;
import com.inghub.sems.model.StockPriceUpdateRequest;

public interface StockService {
    Stock createStock(StockCreateRequest request);

    Stock updateStockPrice(StockPriceUpdateRequest request);

    void deleteStockByName(String stockName);
}