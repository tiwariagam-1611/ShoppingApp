package com.shoppingapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingapp.dto.request.OrderRequestDTO;
import com.shoppingapp.dto.response.OrderResponseDTO;
import com.shoppingapp.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderContoller {

    private static final Logger logger = LoggerFactory.getLogger(OrderContoller.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(@Valid @RequestBody OrderRequestDTO orderRequest) {
        logger.info("Received checkout request for user ID: {}", orderRequest.getUserId());
        OrderResponseDTO order = orderService.checkout(orderRequest);
        logger.info("Checkout successful. Order ID: {} created.", order.getOrderId());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        logger.info("Fetching all orders.");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order details for Order ID: {}", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Long userId) {
        logger.info("Fetching all orders for User ID: {}", userId);
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }
}