package com.inghub.sems.controller;


import com.inghub.sems.entity.Stock;
import com.inghub.sems.service.StockExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/stock-exchange")
public class StockExchangeController {

    private StockExchangeService stockExchangeService;


    @GetMapping("/{exchangeName}")
    public ResponseEntity<List<Stock>> getAllStocks(@PathVariable String exchangeName) {
        List<Stock> stocks = stockExchangeService.getStocksByStockExchange(exchangeName);
        return ResponseEntity.ok(stocks);
    }


    @PostMapping("/{exchangeName}/stock")
    public void addStockToExchange(@PathVariable String exchangeName, @RequestParam String stockName) {
        stockExchangeService.addStockToExchange(exchangeName, stockName);
    }


    @DeleteMapping("/{exchangeName}/stock")
    public void removeStockFromExchange(@PathVariable String exchangeName, @RequestParam String stockName) {
        stockExchangeService.removeStockFromExchange(exchangeName, stockName);
    }

}