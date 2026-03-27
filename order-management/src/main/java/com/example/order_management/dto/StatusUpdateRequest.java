package com.example.order_management.dto;

import com.example.order_management.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "status is mandatory")
    private OrderStatus status;
}
