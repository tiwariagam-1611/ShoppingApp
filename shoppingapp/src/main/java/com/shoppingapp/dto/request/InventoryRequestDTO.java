package com.shoppingapp.dto.request;

public class InventoryRequestDTO {
    private Long productId;
    private Integer availableQuantity;
    private Integer reorderLevel;
    
    public InventoryRequestDTO() {}
    
	public InventoryRequestDTO(Integer availableQuantity, Integer reorderLevel) {
		super();
		this.availableQuantity = availableQuantity;
		this.reorderLevel = reorderLevel;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
    
}
