package com.inghub.sems.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockPriceUpdateRequest {
    @NotNull( message = "name id is required")
    private String stockName;

    @NotNull( message = "New price can not be null")
    @DecimalMin(value = "0.0", message = "New price must be greater than zero", inclusive = false)
    private BigDecimal newPrice;

}