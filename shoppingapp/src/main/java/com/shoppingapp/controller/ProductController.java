package com.shoppingapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shoppingapp.dto.request.ProductRequestDTO;
import com.shoppingapp.dto.response.ProductResponseDTO;
import com.shoppingapp.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO dto) {
        logger.info("Received request to create a new product.");
        ProductResponseDTO response = productService.createProduct(dto);
        logger.info("Product created successfully.");
        return response;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        logger.info("Fetching all products.");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        logger.info("Fetching details for product ID: {}", id);
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id,
                                            @RequestBody ProductRequestDTO dto) {
        logger.info("Received request to update product ID: {}", id);
        ProductResponseDTO response = productService.updateProduct(id, dto);
        logger.info("Product ID: {} updated successfully.", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        logger.info("Received request to delete product ID: {}", id);
        String response = productService.deleteProduct(id);
        logger.info("Product ID: {} deleted successfully.", id);
        return response;
    }
}