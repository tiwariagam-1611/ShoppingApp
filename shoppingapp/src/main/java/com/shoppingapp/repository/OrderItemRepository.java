package com.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingapp.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
