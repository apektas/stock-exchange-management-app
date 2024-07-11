package com.inghub.sems.repository;

import com.inghub.sems.entity.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

    Optional<StockExchange> findByName(String exchangeName);

}