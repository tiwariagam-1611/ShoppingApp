package com.shoppingapp.controller;

import com.shoppingapp.dto.request.InventoryRequestDTO;
import com.shoppingapp.dto.response.InventoryResponseDTO;
import com.shoppingapp.service.InventoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // 1. Create Inventory
    // Path: POST http://localhost:8080/api/inventory
    // Request Body: {"productId": 1, "availableQuantity": 50, "reorderLevel": 10}
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> addInventory(@RequestBody InventoryRequestDTO requestDTO) {
        return ResponseEntity.ok(inventoryService.createInventory(requestDTO));
    }

    // 2. Get Inventory by Product ID
    // Path: GET http://localhost:8080/api/inventory/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getInventoryByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryResponseByProductId(productId));
    }

    // 3. Get all Low Stock items
    // Path: GET http://localhost:8080/api/inventory/low-stock
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponseDTO>> getLowStockItems() {
        return ResponseEntity.ok(inventoryService.getLowStockItems());
    }

    // 4. Get All Inventory
    // Path: GET http://localhost:8080/api/inventory
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    // 5. Update Inventory for a specific product
    // Path: PUT http://localhost:8080/api/inventory/{productId}
    // Request Body: {"availableQuantity": 100, "reorderLevel": 20}
    @PutMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryRequestDTO requestDTO) {
        
        return ResponseEntity.ok(inventoryService.updateInventory(productId, requestDTO));
    }
}