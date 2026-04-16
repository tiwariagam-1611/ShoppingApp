package com.shoppingapp.repository;

import com.shoppingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String newEmail);
	
}