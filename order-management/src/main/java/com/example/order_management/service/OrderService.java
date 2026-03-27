package com.example.order_management.service;

import com.example.order_management.dto.OrderRequest;
import com.example.order_management.model.Order;
import com.example.order_management.model.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request);
    Order getOrder(String orderId);
    Order updateStatus(String orderId, OrderStatus newStatus);
    List<Order> getAllOrders();
}