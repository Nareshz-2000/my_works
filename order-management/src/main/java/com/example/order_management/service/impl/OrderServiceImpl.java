package com.example.order_management.service.impl;

import com.example.order_management.dto.OrderRequest;
import com.example.order_management.exception.InvalidStatusTransitionException;
import com.example.order_management.exception.OrderNotFoundException;
import com.example.order_management.model.Order;
import com.example.order_management.model.OrderStatus;
import com.example.order_management.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final Map<String, Order> store = new ConcurrentHashMap<>();

    @Override
    public Order createOrder(OrderRequest request) {
        log.debug("Creating order for customer : {}", request.getCustomerName());
        String id = UUID.randomUUID().toString();
        Order order = new Order(id, request.getCustomerName(), request.getAmount(), OrderStatus.NEW);
        store.put(id, order);
        return order;
    }

    @Override
    public Order getOrder(String orderId) {
        log.debug("Fetching order for order_id={}", orderId);
        Order order = store.get(orderId);
        if (order == null) {
            log.error("Invalid order_id={}", orderId);
            throw new OrderNotFoundException(orderId);
        }
        return order;
    }

    @Override
    public Order updateStatus(String orderId, OrderStatus newStatus) {
        log.debug("Updating order for order_id={}", orderId);
        Order order = getOrder(orderId);
        validateTransition(order.getStatus(), newStatus);
        order.setStatus(newStatus);
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(store.values());
    }

    private void validateTransition(OrderStatus current, OrderStatus next) {
        boolean valid = (current == OrderStatus.NEW && next == OrderStatus.PROCESSING)
                || (current == OrderStatus.PROCESSING && next == OrderStatus.COMPLETED);
        if (!valid) throw new InvalidStatusTransitionException(current.name(), next.name());
    }
}