package com.shoppingapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingapp.dto.request.ProductRequestDTO;
import com.shoppingapp.dto.response.ProductResponseDTO;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
     

        Product saved = productRepository.save(product);

        return new ProductResponseDTO(
                saved.getProductId(),
                saved.getProductName(),
                saved.getCategory(),
                saved.getPrice()
        );
    }

    public List<ProductResponseDTO> getAllProducts() {

        List<Product> list = productRepository.findAll();
        List<ProductResponseDTO> responseList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            Product p = list.get(i);

            ProductResponseDTO dto = new ProductResponseDTO(
                    p.getProductId(),
                    p.getProductName(),
                    p.getCategory(),
                    p.getPrice()
            );

            responseList.add(dto);
        }

        return responseList;
    }

    public ProductResponseDTO getProductById(Long id) {

        Product p = productRepository.findById(id).orElse(null);

        if (p == null) return null;

        return new ProductResponseDTO(
                p.getProductId(),
                p.getProductName(),
                p.getCategory(),
                p.getPrice()
        );
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {

        Product p = productRepository.findById(id).orElse(null);

        if (p == null) return null;

        p.setProductName(dto.getProductName());
        p.setDescription(dto.getDescription());
        p.setCategory(dto.getCategory());
        p.setPrice(dto.getPrice());
   

        Product updated = productRepository.save(p);

        return new ProductResponseDTO(
                updated.getProductId(),
                updated.getProductName(),
                updated.getCategory(),
                updated.getPrice()
        );
    }

    public String deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            return "Product not found";
        }

        productRepository.deleteById(id);
        return "Product deleted successfully";
    }
}