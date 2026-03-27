package com.example.order_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {

    private String orderId;

    private String customerName;

    private Double amount;

    private OrderStatus status;
}
