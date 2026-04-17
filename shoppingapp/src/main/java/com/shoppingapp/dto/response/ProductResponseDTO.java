package com.shoppingapp.dto.response;

public class ProductResponseDTO {

    private Long productId;
    private String productName;
    private String category;
    private Double price;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long productId, String productName,
                              String category, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}