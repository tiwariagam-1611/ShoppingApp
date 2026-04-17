package com.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shoppingapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}