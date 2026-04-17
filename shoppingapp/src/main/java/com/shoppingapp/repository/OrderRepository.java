package com.shoppingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingapp.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

	List<Order> findByUserUserId(Long userId);
	
}
