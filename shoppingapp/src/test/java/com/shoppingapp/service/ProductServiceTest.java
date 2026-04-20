package com.shoppingapp.service;

import com.shoppingapp.dto.request.ProductRequestDTO;
import com.shoppingapp.dto.response.ProductResponseDTO;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldReturnCreatedDto() {
        ProductRequestDTO request = new ProductRequestDTO("Pen", "Blue pen", "Stationery", 10.0);

        Product saved = new Product();
        saved.setProductId(1L);
        saved.setProductName("Pen");
        saved.setCategory("Stationery");
        saved.setPrice(10.0);

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponseDTO response = productService.createProduct(request);

        assertEquals(1L, response.getProductId());
        assertEquals("Pen", response.getProductName());
    }

    @Test
    void getAllProducts_shouldReturnList() {
        Product p = new Product();
        p.setProductId(1L);
        p.setProductName("P1");
        p.setCategory("C1");
        p.setPrice(20.0);

        when(productRepository.findAll()).thenReturn(List.of(p));

        List<ProductResponseDTO> response = productService.getAllProducts();

        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getProductId());
    }

    @Test
    void getProductById_whenMissing_shouldReturnNull() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        ProductResponseDTO response = productService.getProductById(99L);

        assertNull(response);
    }

    @Test
    void deleteProduct_whenMissing_shouldReturnNotFoundMessage() {
        when(productRepository.existsById(99L)).thenReturn(false);

        String response = productService.deleteProduct(99L);

        assertEquals("Product not found", response);
    }
}
