package com.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingapp.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

	boolean existsByEmail(String newEmail);

}