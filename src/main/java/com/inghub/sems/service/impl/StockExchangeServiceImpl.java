package com.inghub.sems.service.impl;

import com.inghub.sems.entity.Stock;
import com.inghub.sems.entity.StockExchange;
import com.inghub.sems.exception.EntityAlreadyExistsException;
import com.inghub.sems.repository.StockExchangeRepository;
import com.inghub.sems.repository.StockRepository;
import com.inghub.sems.service.StockExchangeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StockExchangeServiceImpl implements StockExchangeService {
    private StockExchangeRepository stockExchangeRepository;
    private StockRepository stockRepository;


    @Override
    @Transactional
    public List<Stock> getStocksByStockExchange(String exchangeName) {

        StockExchange exchange = stockExchangeRepository.findByName(exchangeName).orElseThrow(()
                -> new EntityNotFoundException("Stock Exchange not found with name : " + exchangeName));

        return  exchange.getStocks().stream().toList();
    }


    @Override
    @Transactional
    public void addStockToExchange(String exchangeName, String stockName) {
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with name: " + stockName));

        StockExchange stockExchange = stockExchangeRepository.findByName(exchangeName)
                .orElseThrow(() -> new EntityNotFoundException("Stock Exchange not found with name: " + exchangeName));



        Set<Stock> currentStocks = stockExchange.getStocks();

        if (currentStocks.contains(stock)) {
            throw new EntityAlreadyExistsException(String.format("Stock (%s) already in stock exchange(%s) ", stockName, exchangeName));
        }

        currentStocks.add(stock);


        boolean liveInMarket = currentStocks.size() >= 5;
        stockExchange.setLiveInMarket(liveInMarket);
        stockExchange.setStocks(currentStocks);
        stockExchangeRepository.save(stockExchange);
    }

    @Override
    @Transactional
    public void removeStockFromExchange(String exchangeName, String stockName) {
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with name: " + stockName));

        StockExchange stockExchange = stockExchangeRepository.findByName(exchangeName)
                .orElseThrow(() -> new EntityNotFoundException("Stock Exchange not found with name: " + exchangeName));



        Set<Stock> currentStocks = stockExchange.getStocks();

        if (!currentStocks.contains(stock)) {
            throw new EntityNotFoundException(String.format("Stock (%s) not found in stock exchange(%s) ", stockName, exchangeName));
        }

        currentStocks.remove(stock);

        boolean liveInMarket = currentStocks.size() >= 5;
        stockExchange.setLiveInMarket(liveInMarket);
        stockExchange.setStocks(currentStocks);
        stockExchangeRepository.save(stockExchange);
    }

}
