package com.ztsc.backend.warehouse.controller;

import com.ztsc.backend.warehouse.entity.Inventory;
import com.ztsc.backend.warehouse.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(
            InventoryService inventoryService
    ) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {

        return ResponseEntity.ok(
                "Welcome to Warehouse Dashboard"
        );
    }

    @PostMapping("/inventory")
    public ResponseEntity<Inventory> createInventory(
            @RequestBody Inventory inventory
    ) {

        Inventory created =
                inventoryService.createInventory(
                        inventory
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>>
    getAllInventory() {

        return ResponseEntity.ok(
                inventoryService.getAllInventory()
        );
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<Inventory>
    getInventoryById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                inventoryService.getInventoryById(id)
        );
    }

    @GetMapping("/inventory/status/{status}")
    public ResponseEntity<List<Inventory>>
    getInventoryByStatus(
            @PathVariable String status
    ) {

        return ResponseEntity.ok(
                inventoryService
                        .getInventoryByStatus(status)
        );
    }

    @GetMapping("/inventory/category/{category}")
    public ResponseEntity<List<Inventory>>
    getInventoryByCategory(
            @PathVariable String category
    ) {

        return ResponseEntity.ok(
                inventoryService
                        .getInventoryByCategory(category)
        );
    }

    @GetMapping("/inventory/low-stock")
    public ResponseEntity<List<Inventory>>
    getLowStockInventory() {

        return ResponseEntity.ok(
                inventoryService
                        .getLowStockInventory()
        );
    }

    @PutMapping("/inventory/{id}")
    public ResponseEntity<Inventory>
    updateInventory(
            @PathVariable Long id,
            @RequestBody Inventory inventory
    ) {

        return ResponseEntity.ok(
                inventoryService.updateInventory(
                        id,
                        inventory
                )
        );
    }

    @PatchMapping("/inventory/{id}/stock-in")
    public ResponseEntity<Inventory>
    stockIn(
            @PathVariable Long id,
            @RequestParam Integer quantity
    ) {

        return ResponseEntity.ok(
                inventoryService.stockIn(
                        id,
                        quantity
                )
        );
    }

    @PatchMapping("/inventory/{id}/stock-out")
    public ResponseEntity<Inventory>
    stockOut(
            @PathVariable Long id,
            @RequestParam Integer quantity
    ) {

        return ResponseEntity.ok(
                inventoryService.stockOut(
                        id,
                        quantity
                )
        );
    }

    @DeleteMapping("/inventory/{id}")
    public ResponseEntity<Void>
    deleteInventory(
            @PathVariable Long id
    ) {

        inventoryService.deleteInventory(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}