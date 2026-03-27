package com.example.order_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank(message = "customerName is mandatory")
    private String customerName;

    @NotNull(message = "amount is mandatory")
    @Positive(message = "amount must be greater than 0")
    private Double amount;
}
