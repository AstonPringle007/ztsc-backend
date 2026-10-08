package com.ztsc.backend.warehouse.repository;

import com.ztsc.backend.warehouse.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByItemCode(String itemCode);

    boolean existsByItemCode(String itemCode);

    List<Inventory> findByStatus(String status);

    List<Inventory> findByCategory(String category);

    List<Inventory> findByQuantityLessThanEqual(
            Integer quantity
    );
}