package com.shoppingapp.dto.response;

public class InventoryResponseDTO {

    private Long inventoryId;
    private Long productId;
    private String productName;
    private Integer availableQuantity;
    private Integer reorderLevel;
    private String status;

    public InventoryResponseDTO() {
    }

    public InventoryResponseDTO(Long inventoryId, Long productId, String productName,
                                Integer availableQuantity, Integer reorderLevel, String status) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.productName = productName;
        this.availableQuantity = availableQuantity;
        this.reorderLevel = reorderLevel;
        this.status = status;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Builder {
        private Long inventoryId;
        private Long productId;
        private String productName;
        private Integer availableQuantity;
        private Integer reorderLevel;
        private String status;

        public Builder inventoryId(Long inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder availableQuantity(Integer availableQuantity) {
            this.availableQuantity = availableQuantity;
            return this;
        }

        public Builder reorderLevel(Integer reorderLevel) {
            this.reorderLevel = reorderLevel;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public InventoryResponseDTO build() {
            return new InventoryResponseDTO(
                inventoryId,
                productId,
                productName,
                availableQuantity,
                reorderLevel,
                status
            );
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}