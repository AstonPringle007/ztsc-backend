package com.ztsc.backend.supplier.controller;

import com.ztsc.backend.supplier.entity.Supplier;
import com.ztsc.backend.supplier.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(
            SupplierService supplierService
    ) {
        this.supplierService = supplierService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(
            @RequestBody Supplier supplier
    ) {

        Supplier created =
                supplierService.createSupplier(
                        supplier
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Supplier>>
    getAllSuppliers() {

        return ResponseEntity.ok(
                supplierService.getAllSuppliers()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Supplier>
    getSupplierById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );
    }

    // GET BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Supplier>>
    getSuppliersByStatus(
            @PathVariable String status
    ) {

        return ResponseEntity.ok(
                supplierService
                        .getSuppliersByStatus(status)
        );
    }

    // GET BY CATEGORY
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Supplier>>
    getSuppliersByCategory(
            @PathVariable String category
    ) {

        return ResponseEntity.ok(
                supplierService
                        .getSuppliersByCategory(category)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Supplier>
    updateSupplier(
            @PathVariable Long id,
            @RequestBody Supplier supplier
    ) {

        return ResponseEntity.ok(
                supplierService.updateSupplier(
                        id,
                        supplier
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteSupplier(
            @PathVariable Long id
    ) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();
    }
}