package com.shoppingapp.controller;

import com.shoppingapp.dto.request.InventoryRequestDTO;
import com.shoppingapp.dto.response.InventoryResponseDTO;
import com.shoppingapp.service.InventoryService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    // 1. Create Inventory
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> addInventory(@RequestBody InventoryRequestDTO requestDTO) {
        logger.info("Received request to add inventory for product ID: {}", requestDTO.getProductId());
        InventoryResponseDTO response = inventoryService.createInventory(requestDTO);
        logger.info("Successfully created inventory for product ID: {}", response.getProductId());
        return ResponseEntity.ok(response);
    }

    // 2. Get Inventory by Product ID
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getInventoryByProductId(@PathVariable Long productId) {
        logger.info("Fetching inventory details for product ID: {}", productId);
        return ResponseEntity.ok(inventoryService.getInventoryResponseByProductId(productId));
    }

    // 3. Get all Low Stock items
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponseDTO>> getLowStockItems() {
        logger.info("Fetching all low-stock items.");
        return ResponseEntity.ok(inventoryService.getLowStockItems());
    }

    // 4. Get All Inventory
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {
        logger.info("Fetching all inventory items.");
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    // 5. Update Inventory for a specific product
    @PutMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryRequestDTO requestDTO) {
        
        logger.info("Received update request for product ID: {}", productId);
        InventoryResponseDTO response = inventoryService.updateInventory(productId, requestDTO);
        logger.info("Successfully updated inventory for product ID: {}", productId);
        return ResponseEntity.ok(response);
    }
}