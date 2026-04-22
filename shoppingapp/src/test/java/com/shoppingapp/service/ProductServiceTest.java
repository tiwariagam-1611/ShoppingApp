package com.shoppingapp.service;

import com.shoppingapp.dto.request.ProductRequestDTO;
import com.shoppingapp.dto.response.ProductResponseDTO;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product saveProduct(String name, String category, double price) {
        Product product = new Product();
        product.setProductName(name);
        product.setDescription(name + " description");
        product.setCategory(category);
        product.setPrice(price);
        return productRepository.save(product);
    }

    @Test
    void createProduct_shouldReturnCreatedDto() {
        ProductRequestDTO request = new ProductRequestDTO("Pen", "Blue pen", "Stationery", 10.0);

        ProductResponseDTO response = productService.createProduct(request);

        assertTrue(response.getProductId() > 0);
        assertEquals("Pen", response.getProductName());
    }

    @Test
    void getAllProducts_shouldReturnList() {
        saveProduct("P1", "C1", 20.0);

        List<ProductResponseDTO> response = productService.getAllProducts();

        assertEquals(1, response.size());
        assertEquals("P1", response.get(0).getProductName());
    }

    @Test
    void getProductById_whenMissing_shouldReturnNull() {
        ProductResponseDTO response = productService.getProductById(99L);

        assertNull(response);
    }

    @Test
    void getProductById_whenFound_shouldReturnDto() {
        Product existing = saveProduct("Notebook", "Stationery", 75.0);

        ProductResponseDTO response = productService.getProductById(existing.getProductId());

        assertEquals(existing.getProductId(), response.getProductId());
        assertEquals("Notebook", response.getProductName());
    }

    @Test
    void updateProduct_whenFound_shouldReturnUpdatedDto() {
        Product existing = saveProduct("Old", "Electronics", 100.0);
        ProductRequestDTO request = new ProductRequestDTO("Charger", "USB-C", "Electronics", 499.0);

        ProductResponseDTO response = productService.updateProduct(existing.getProductId(), request);

        assertEquals(existing.getProductId(), response.getProductId());
        assertEquals("Charger", response.getProductName());
        assertEquals("Electronics", response.getCategory());
        assertEquals(499.0, response.getPrice());
    }

    @Test
    void updateProduct_whenMissing_shouldReturnNull() {
        ProductRequestDTO request = new ProductRequestDTO("X", "Y", "Z", 1.0);

        ProductResponseDTO response = productService.updateProduct(700L, request);

        assertNull(response);
    }

    @Test
    void deleteProduct_whenMissing_shouldReturnNotFoundMessage() {
        String response = productService.deleteProduct(99L);

        assertEquals("Product not found", response);
    }

    @Test
    void deleteProduct_whenFound_shouldDeleteAndReturnSuccessMessage() {
        Product existing = saveProduct("DeleteMe", "C", 11.0);

        String response = productService.deleteProduct(existing.getProductId());

        assertEquals("Product deleted successfully", response);
        assertFalse(productRepository.existsById(existing.getProductId()));
    }
}
