package com.shoppingapp.dto.request;

public class ProductRequestDTO {

    private String productName;
    private String description;
    private String category;
    private Double price;

    public ProductRequestDTO() {}

    public ProductRequestDTO(String productName, String description,
                             String category, Double price) {
        this.productName = productName;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}