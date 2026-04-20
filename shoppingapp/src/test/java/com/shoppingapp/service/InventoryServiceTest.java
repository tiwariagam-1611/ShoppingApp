package com.shoppingapp.service;

import com.shoppingapp.dto.request.InventoryRequestDTO;
import com.shoppingapp.dto.response.InventoryResponseDTO;
import com.shoppingapp.model.Inventory;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.InventoryRepository;
import com.shoppingapp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void createInventory_shouldReturnDto() {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Milk");

        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setProductId(1L);
        request.setAvailableQuantity(20);
        request.setReorderLevel(5);

        Inventory saved = new Inventory();
        saved.setInventoryId(100L);
        saved.setProduct(product);
        saved.setAvailableQuantity(20);
        saved.setReorderLevel(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(saved);

        InventoryResponseDTO response = inventoryService.createInventory(request);

        assertEquals(100L, response.getInventoryId());
        assertEquals("IN_STOCK", response.getStatus());
    }

    @Test
    void getInventoryByProductId_whenMissing_shouldThrow() {
        when(inventoryRepository.findByProduct_ProductId(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> inventoryService.getInventoryResponseByProductId(99L));
    }

    @Test
    void deductStock_whenInsufficient_shouldThrow() {
        Product product = new Product();
        product.setProductId(1L);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setAvailableQuantity(2);
        inventory.setReorderLevel(1);

        when(inventoryRepository.findByProduct_ProductId(1L)).thenReturn(Optional.of(inventory));

        assertThrows(RuntimeException.class, () -> inventoryService.deductStock(1L, 3));
    }

    @Test
    void deductStock_whenEnough_shouldSaveUpdatedInventory() {
        Product product = new Product();
        product.setProductId(1L);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setAvailableQuantity(10);
        inventory.setReorderLevel(3);

        when(inventoryRepository.findByProduct_ProductId(1L)).thenReturn(Optional.of(inventory));

        inventoryService.deductStock(1L, 4);

        assertEquals(6, inventory.getAvailableQuantity());
        verify(inventoryRepository).save(inventory);
    }
}
