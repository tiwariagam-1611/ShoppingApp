package com.shoppingapp.service;

import com.shoppingapp.dto.request.InventoryRequestDTO;
import com.shoppingapp.dto.response.InventoryResponseDTO;
import com.shoppingapp.model.Inventory;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.InventoryRepository;
import com.shoppingapp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product saveProduct(String name) {
        Product product = new Product();
        product.setProductName(name);
        product.setDescription(name + " description");
        product.setCategory("General");
        product.setPrice(50.0);
        return productRepository.save(product);
    }

    private Inventory saveInventory(Product product, int available, int reorder) {
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setAvailableQuantity(available);
        inventory.setReorderLevel(reorder);
        return inventoryRepository.save(inventory);
    }

    @Test
    void createInventory_shouldReturnDto() {
        Product product = saveProduct("Milk");

        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setProductId(product.getProductId());
        request.setAvailableQuantity(20);
        request.setReorderLevel(5);

        InventoryResponseDTO response = inventoryService.createInventory(request);

        assertEquals(product.getProductId(), response.getProductId());
        assertEquals("IN_STOCK", response.getStatus());
    }

    @Test
    void createInventory_whenProductMissing_shouldThrow() {
        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setProductId(999L);

        assertThrows(RuntimeException.class, () -> inventoryService.createInventory(request));
    }

    @Test
    void getAllInventory_shouldReturnMappedDtos() {
        Product inStockProduct = saveProduct("Milk");
        Product lowStockProduct = saveProduct("Bread");
        saveInventory(inStockProduct, 10, 3);
        saveInventory(lowStockProduct, 2, 2);

        List<InventoryResponseDTO> response = inventoryService.getAllInventory();

        assertEquals(2, response.size());
        assertEquals("IN_STOCK", response.get(0).getStatus());
        assertEquals("LOW_STOCK", response.get(1).getStatus());
    }

    @Test
    void getInventoryByProductId_whenFound_shouldReturnMappedDto() {
        Product product = saveProduct("Eggs");
        Inventory inventory = saveInventory(product, 9, 2);

        InventoryResponseDTO response = inventoryService.getInventoryResponseByProductId(product.getProductId());

        assertEquals(inventory.getInventoryId(), response.getInventoryId());
        assertEquals("Eggs", response.getProductName());
    }

    @Test
    void getLowStockItems_shouldReturnMappedDtos() {
        Product lowStockProduct = saveProduct("Juice");
        saveInventory(lowStockProduct, 1, 2);

        List<InventoryResponseDTO> response = inventoryService.getLowStockItems();

        assertEquals(1, response.size());
        assertEquals("LOW_STOCK", response.get(0).getStatus());
        assertEquals(lowStockProduct.getProductId(), response.get(0).getProductId());
    }

    @Test
    void updateInventory_shouldUpdateOnlyProvidedFields() {
        Product product = saveProduct("Milk");
        saveInventory(product, 12, 4);

        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setAvailableQuantity(8);

        InventoryResponseDTO response = inventoryService.updateInventory(product.getProductId(), request);

        assertEquals(8, response.getAvailableQuantity());
        assertEquals(4, response.getReorderLevel());
        assertEquals("Milk", response.getProductName());
    }

    @Test
    void updateInventory_whenMissing_shouldThrow() {
        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setAvailableQuantity(7);

        assertThrows(RuntimeException.class, () -> inventoryService.updateInventory(77L, request));
    }

    @Test
    void getInventoryByProductId_whenMissing_shouldThrow() {
        assertThrows(RuntimeException.class, () -> inventoryService.getInventoryResponseByProductId(99L));
    }

    @Test
    void deductStock_whenInsufficient_shouldThrow() {
        Product product = saveProduct("Milk");
        saveInventory(product, 2, 1);

        assertThrows(RuntimeException.class, () -> inventoryService.deductStock(product.getProductId(), 3));
    }

    @Test
    void deductStock_whenEnough_shouldSaveUpdatedInventory() {
        Product product = saveProduct("Milk");
        saveInventory(product, 10, 3);

        inventoryService.deductStock(product.getProductId(), 4);

        int remaining = inventoryRepository.findByProduct_ProductId(product.getProductId())
                .orElseThrow()
                .getAvailableQuantity();
        assertEquals(6, remaining);
    }
}
