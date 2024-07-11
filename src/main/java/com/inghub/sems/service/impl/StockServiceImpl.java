package com.inghub.sems.service.impl;

import com.inghub.sems.entity.Stock;
import com.inghub.sems.entity.StockExchange;
import com.inghub.sems.exception.EntityAlreadyExistsException;
import com.inghub.sems.model.StockCreateRequest;
import com.inghub.sems.model.StockPriceUpdateRequest;
import com.inghub.sems.repository.StockExchangeRepository;
import com.inghub.sems.repository.StockRepository;
import com.inghub.sems.service.StockService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;

    @Override
    public Stock createStock(StockCreateRequest request) {
        if (stockRepository.findByName(request.getName()).isPresent()) {
            throw  new EntityAlreadyExistsException("Stock already exists with name : " + request.getName());
        }

        Stock stock = new Stock();
        stock.setCurrentPrice(request.getCurrentPrice());
        stock.setName(request.getName());
        stock.setDescription(request.getDescription());
        return stockRepository.save(stock);
    }


    @Override
    public Stock updateStockPrice(StockPriceUpdateRequest request) {
        Stock stock = stockRepository.findByName(request.getStockName()).orElseThrow(()
                -> new EntityNotFoundException("Stock not found with name : " + request.getStockName()));
        stock.setCurrentPrice(request.getNewPrice());
        stock.setLastUpdate(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStockByName(String stockName) {
        Stock stock = stockRepository.findByName(stockName).orElseThrow(()
                -> new EntityNotFoundException("Stock not found with name: " + stockName));

        // When deleting stock please consider that Stock Exchange might be effected
        // because of the condition (Stock exchange having less than 5 stocks will become liveMarket prop false)

        // Get Stock Exchanges linked to stock
        // Update liveInMarket Status after stock deletion
        for(StockExchange exchange : stock.getStockExchanges()){
            boolean marketStatusAfterRemoval = exchange.getStocks().size() > 5;
            exchange.getStocks().remove(stock);
            exchange.setLiveInMarket(marketStatusAfterRemoval);
        }

        // Save all stockExchange once  - if the size is too big then it is better to save in batch.
        stockExchangeRepository.saveAll(stock.getStockExchanges());
        stockRepository.delete(stock);
    }

}
