package com.ztsc.backend.warehouse.service;

import com.ztsc.backend.warehouse.entity.Inventory;
import com.ztsc.backend.warehouse.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(
            InventoryRepository inventoryRepository
    ) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory createInventory(
            Inventory inventory
    ) {

        validateInventory(inventory);

        if (inventoryRepository.existsByItemCode(
                inventory.getItemCode())) {

            throw new IllegalArgumentException(
                    "Item code already exists"
            );
        }

        inventory.setId(null);

        if (inventory.getStatus() == null ||
                inventory.getStatus().isBlank()) {

            inventory.setStatus(
                    determineStatus(
                            inventory.getQuantity(),
                            inventory.getMinimumStock()
                    )
            );
        }

        LocalDateTime now = LocalDateTime.now();

        inventory.setCreatedAt(now);
        inventory.setUpdatedAt(now);

        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory() {

        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {

        return inventoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory item not found: " + id
                        )
                );
    }

    public List<Inventory> getInventoryByStatus(
            String status
    ) {

        return inventoryRepository.findByStatus(
                status.toUpperCase()
        );
    }

    public List<Inventory> getInventoryByCategory(
            String category
    ) {

        return inventoryRepository.findByCategory(
                category
        );
    }

    public List<Inventory> getLowStockInventory() {

        return inventoryRepository
                .findAll()
                .stream()
                .filter(item ->
                        item.getQuantity()
                                <= item.getMinimumStock()
                )
                .toList();
    }

    public Inventory updateInventory(
            Long id,
            Inventory updatedInventory
    ) {

        Inventory existing =
                getInventoryById(id);

        if (updatedInventory.getItemName() == null ||
                updatedInventory.getItemName().isBlank()) {

            throw new IllegalArgumentException(
                    "Item name is required"
            );
        }

        if (updatedInventory.getQuantity() == null ||
                updatedInventory.getQuantity() < 0) {

            throw new IllegalArgumentException(
                    "Quantity cannot be negative"
            );
        }

        if (updatedInventory.getMinimumStock() == null ||
                updatedInventory.getMinimumStock() < 0) {

            throw new IllegalArgumentException(
                    "Minimum stock cannot be negative"
            );
        }

        existing.setItemName(
                updatedInventory.getItemName()
        );

        existing.setCategory(
                updatedInventory.getCategory()
        );

        existing.setSupplier(
                updatedInventory.getSupplier()
        );

        existing.setWarehouseLocation(
                updatedInventory.getWarehouseLocation()
        );

        existing.setQuantity(
                updatedInventory.getQuantity()
        );

        existing.setMinimumStock(
                updatedInventory.getMinimumStock()
        );

        existing.setUnit(
                updatedInventory.getUnit()
        );

        existing.setStatus(
                determineStatus(
                        updatedInventory.getQuantity(),
                        updatedInventory.getMinimumStock()
                )
        );

        existing.setUpdatedAt(
                LocalDateTime.now()
        );

        return inventoryRepository.save(existing);
    }

    public Inventory stockIn(
            Long id,
            Integer quantity
    ) {

        if (quantity == null || quantity <= 0) {

            throw new IllegalArgumentException(
                    "Stock-in quantity must be greater than zero"
            );
        }

        Inventory inventory =
                getInventoryById(id);

        inventory.setQuantity(
                inventory.getQuantity() + quantity
        );

        inventory.setStatus(
                determineStatus(
                        inventory.getQuantity(),
                        inventory.getMinimumStock()
                )
        );

        inventory.setUpdatedAt(
                LocalDateTime.now()
        );

        return inventoryRepository.save(inventory);
    }

    public Inventory stockOut(
            Long id,
            Integer quantity
    ) {

        if (quantity == null || quantity <= 0) {

            throw new IllegalArgumentException(
                    "Stock-out quantity must be greater than zero"
            );
        }

        Inventory inventory =
                getInventoryById(id);

        if (inventory.getQuantity() < quantity) {

            throw new IllegalArgumentException(
                    "Insufficient stock"
            );
        }

        inventory.setQuantity(
                inventory.getQuantity() - quantity
        );

        inventory.setStatus(
                determineStatus(
                        inventory.getQuantity(),
                        inventory.getMinimumStock()
                )
        );

        inventory.setUpdatedAt(
                LocalDateTime.now()
        );

        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {

        Inventory inventory =
                getInventoryById(id);

        inventoryRepository.delete(inventory);
    }

    private void validateInventory(
            Inventory inventory
    ) {

        if (inventory.getItemCode() == null ||
                inventory.getItemCode().isBlank()) {

            throw new IllegalArgumentException(
                    "Item code is required"
            );
        }

        if (inventory.getItemName() == null ||
                inventory.getItemName().isBlank()) {

            throw new IllegalArgumentException(
                    "Item name is required"
            );
        }

        if (inventory.getQuantity() == null ||
                inventory.getQuantity() < 0) {

            throw new IllegalArgumentException(
                    "Quantity cannot be negative"
            );
        }

        if (inventory.getMinimumStock() == null ||
                inventory.getMinimumStock() < 0) {

            throw new IllegalArgumentException(
                    "Minimum stock cannot be negative"
            );
        }
    }

    private String determineStatus(
            Integer quantity,
            Integer minimumStock
    ) {

        if (quantity == 0) {
            return "OUT_OF_STOCK";
        }

        if (quantity <= minimumStock) {
            return "LOW_STOCK";
        }

        return "IN_STOCK";
    }
}