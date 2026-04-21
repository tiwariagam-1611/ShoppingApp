package com.shoppingapp.controller;

import com.shoppingapp.dto.request.UserRequestDto;
import com.shoppingapp.dto.response.UserResponseDto;
import com.shoppingapp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        logger.info("Received request to create a new user.");
        UserResponseDto created = userService.createUser(dto);
        logger.info("User created successfully with ID: {}", created.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/users
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        logger.info("Fetching all users.");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        logger.info("Fetching details for user ID: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto dto
    ) {
        logger.info("Received update request for user ID: {}", id);
        UserResponseDto updatedUser = userService.updateUser(id, dto);
        logger.info("User ID: {} updated successfully.", id);
        return ResponseEntity.ok(updatedUser);
    }
}