package com.shoppingapp.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {
	@NotNull(message = "User ID is required")
	private Long userId;
	@NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequestDTO> items;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<OrderItemRequestDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemRequestDTO> items) {
		this.items = items;
	}
    
}
