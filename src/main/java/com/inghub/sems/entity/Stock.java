package com.inghub.sems.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "STOCK", indexes = { @Index(name = "index_stock_name", columnList = "name")})
@JsonIgnoreProperties({"stockExchanges"})
public class Stock {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private BigDecimal currentPrice;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ManyToMany(mappedBy = "stocks", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<StockExchange> stockExchanges = new HashSet<>();
}
