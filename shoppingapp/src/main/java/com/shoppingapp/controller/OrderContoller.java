package com.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingapp.dto.request.OrderRequestDTO;
import com.shoppingapp.dto.response.OrderResponseDTO;
import com.shoppingapp.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class OrderContoller {
	@Autowired
	private OrderService orderService;
	@PostMapping("/checkout")
	public ResponseEntity<OrderResponseDTO> checkout(@Valid @RequestBody OrderRequestDTO orderRequest) {
		OrderResponseDTO order = orderService.checkout(orderRequest);
		return ResponseEntity.ok(order);
	}
	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	@GetMapping("/orders/{id}")
	public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.getOrderById(id));
	}
	@GetMapping("/orders/user/{userId}")
	public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
	}
	
}
