package com.shoppingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    
    private Integer availableQuantity;
    private Integer reorderLevel;

    // This links your inventory record to a specific product
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

	public Inventory(Product product, Integer availableQuantity, Integer reorderLevel) {
		super();
		this.product = product;
		this.availableQuantity = availableQuantity;
		this.reorderLevel = reorderLevel;
	}
    
	public Inventory () {}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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