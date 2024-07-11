package com.inghub.sems.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class StockCreateRequest {
    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "description is required")
    @NotEmpty(message = "description cannot be empty")
    private String description;

    @DecimalMin(value = "0", inclusive = false, message = "price should be greater than 0")
    @NotNull(message = "price is required")
    private BigDecimal currentPrice;
}