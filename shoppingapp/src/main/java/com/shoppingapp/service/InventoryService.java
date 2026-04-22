package com.shoppingapp.service;
 
import com.shoppingapp.dto.request.InventoryRequestDTO;
import com.shoppingapp.dto.response.InventoryResponseDTO;
import com.shoppingapp.model.Inventory;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.InventoryRepository;
import com.shoppingapp.repository.ProductRepository; // From Student 2
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class InventoryService {
 
    @Autowired
    private InventoryRepository inventoryRepository;
 
    @Autowired
    private ProductRepository productRepository; // Needed to link Product to Inventory
    @Autowired
    private NotificationService notificationService; // For low stock alerts
 
    // Create New Inventory using DTO
    @Transactional
    public InventoryResponseDTO createInventory(InventoryRequestDTO request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getProductId()));
 
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setAvailableQuantity(request.getAvailableQuantity());
        inventory.setReorderLevel(request.getReorderLevel());
 
        Inventory savedInventory = inventoryRepository.save(inventory);
        return convertToResponseDTO(savedInventory);
    }
 
    // Get All Inventory as DTOs
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
 
    // Get Single Inventory by Product ID as DTO
    public InventoryResponseDTO getInventoryResponseByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for Product ID: " + productId));
        return convertToResponseDTO(inventory);
    }
 
    // Get Low Stock Items as DTOs
    public List<InventoryResponseDTO> getLowStockItems() {
        return inventoryRepository.findLowStockItems()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
 
    // Update Inventory using DTO data
    @Transactional
    public InventoryResponseDTO updateInventory(Long productId, InventoryRequestDTO request) {
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for Product ID: " + productId));
 
        if (request.getAvailableQuantity() != null) {
            inventory.setAvailableQuantity(request.getAvailableQuantity());
        }
        if (request.getReorderLevel() != null) {
            inventory.setReorderLevel(request.getReorderLevel());
        }
 
        return convertToResponseDTO(inventoryRepository.save(inventory));
    }
 
    /**
     * Core Logic: Deduct stock after checkout
     * This remains focused on Entities as it's an internal system call from Student 4
     */
    @Transactional
    public void deductStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for Product ID: " + productId));
 
        if (inventory.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for Product ID: " + productId);
        }
 
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventoryRepository.save(inventory);
 
        if (inventory.getAvailableQuantity() <= inventory.getReorderLevel()) {
        	notificationService.checkForLowStock(productId, inventory.getAvailableQuantity());
            System.out.println("ALERT: Low stock for Product " + productId);
        }
    }
 
    // --- Helper Method: Mapper ---
    private InventoryResponseDTO convertToResponseDTO(Inventory inventory) {
        return InventoryResponseDTO.builder()
                .inventoryId(inventory.getInventoryId())
                .productId(inventory.getProduct().getProductId())
                .productName(inventory.getProduct().getProductName())
                .availableQuantity(inventory.getAvailableQuantity())
                .reorderLevel(inventory.getReorderLevel())
                .status(inventory.getAvailableQuantity() <= inventory.getReorderLevel() ? "LOW_STOCK" : "IN_STOCK")
                .build();
    }
}