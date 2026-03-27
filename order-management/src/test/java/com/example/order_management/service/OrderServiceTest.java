package com.example.order_management.service;

import com.example.order_management.dto.OrderRequest;
import com.example.order_management.exception.InvalidStatusTransitionException;
import com.example.order_management.exception.OrderNotFoundException;
import com.example.order_management.model.Order;
import com.example.order_management.model.OrderStatus;
import com.example.order_management.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl();
    }

    @Test
    void createOrder_shouldReturnOrderWithStatusNew() {
        OrderRequest request = new OrderRequest();
        request.setCustomerName("Naresh");
        request.setAmount(150.0);

        Order order = orderService.createOrder(request);

        assertNotNull(order.getOrderId());
        assertEquals("Naresh", order.getCustomerName());
        assertEquals(150.0, order.getAmount());
        assertEquals(OrderStatus.NEW, order.getStatus());
    }

    @Test
    void getOrder_shouldThrowWhenNotFound() {
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder("12345"));
    }

    @Test
    void updateStatus_validTransition_newToProcessing() {
        OrderRequest request = new OrderRequest();
        request.setCustomerName("Ram");
        request.setAmount(200.0);
        Order created = orderService.createOrder(request);

        Order updated = orderService.updateStatus(created.getOrderId(), OrderStatus.PROCESSING);

        assertEquals(OrderStatus.PROCESSING, updated.getStatus());
    }

    @Test
    void updateStatus_invalidTransition_shouldThrow() {
        OrderRequest request = new OrderRequest();
        request.setCustomerName("kannan");
        request.setAmount(99.0);
        Order created = orderService.createOrder(request);

        assertThrows(InvalidStatusTransitionException.class,
                () -> orderService.updateStatus(created.getOrderId(), OrderStatus.COMPLETED));
    }
}
