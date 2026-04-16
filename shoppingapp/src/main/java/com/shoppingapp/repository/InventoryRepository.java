package com.shoppingapp.repository;

import com.shoppingapp.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProduct_ProductId(Long productId);

    @Query("SELECT i FROM Inventory i WHERE i.availableQuantity <= i.reorderLevel")
    List<Inventory> findLowStockItems();
}
