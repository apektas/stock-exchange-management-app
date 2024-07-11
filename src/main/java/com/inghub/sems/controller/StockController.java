package com.inghub.sems.controller;

import com.inghub.sems.entity.Stock;
import com.inghub.sems.model.StockCreateRequest;
import com.inghub.sems.model.StockPriceUpdateRequest;
import com.inghub.sems.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody @Valid StockCreateRequest request) {
        Stock createdStock = stockService.createStock(request);
        return ResponseEntity.ok(createdStock);
    }

    @PutMapping
    public ResponseEntity<Stock> updateCurrentPrice(@RequestBody @Valid StockPriceUpdateRequest request) {
        Stock updatedStock = stockService.updateStockPrice(request);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{stockName}")
    public void deleteStockByName(@PathVariable String stockName) {
        stockService.deleteStockByName(stockName);
    }

}
