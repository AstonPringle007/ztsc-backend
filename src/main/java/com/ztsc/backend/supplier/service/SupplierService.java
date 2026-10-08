package com.ztsc.backend.supplier.service;

import com.ztsc.backend.supplier.entity.Supplier;
import com.ztsc.backend.supplier.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(
            SupplierRepository supplierRepository
    ) {
        this.supplierRepository = supplierRepository;
    }

    // CREATE
    public Supplier createSupplier(Supplier supplier) {

        if (supplier.getName() == null ||
                supplier.getName().isBlank()) {

            throw new IllegalArgumentException(
                    "Supplier name is required"
            );
        }

        if (supplier.getSupplierCode() == null ||
                supplier.getSupplierCode().isBlank()) {

            throw new IllegalArgumentException(
                    "Supplier code is required"
            );
        }

        if (supplier.getEmail() == null ||
                supplier.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Supplier email is required"
            );
        }

        if (supplierRepository
                .existsBySupplierCode(
                        supplier.getSupplierCode()
                )) {

            throw new IllegalArgumentException(
                    "Supplier code already exists"
            );
        }

        if (supplierRepository
                .existsByEmail(
                        supplier.getEmail()
                )) {

            throw new IllegalArgumentException(
                    "Supplier email already exists"
            );
        }

        supplier.setId(null);

        if (supplier.getStatus() == null ||
                supplier.getStatus().isBlank()) {

            supplier.setStatus("ACTIVE");
        }

        if (supplier.getRating() == null) {
            supplier.setRating(0.0);
        }

        LocalDateTime now = LocalDateTime.now();

        supplier.setCreatedAt(now);
        supplier.setUpdatedAt(now);

        return supplierRepository.save(supplier);
    }

    // GET ALL
    public List<Supplier> getAllSuppliers() {

        return supplierRepository.findAll();
    }

    // GET BY ID
    public Supplier getSupplierById(Long id) {

        return supplierRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found: " + id
                        )
                );
    }

    // GET BY STATUS
    public List<Supplier> getSuppliersByStatus(
            String status
    ) {

        return supplierRepository
                .findByStatus(
                        status.toUpperCase()
                );
    }

    // GET BY CATEGORY
    public List<Supplier> getSuppliersByCategory(
            String category
    ) {

        return supplierRepository
                .findByCategory(category);
    }

    // UPDATE
    public Supplier updateSupplier(
            Long id,
            Supplier updatedSupplier
    ) {

        Supplier existing =
                getSupplierById(id);

        existing.setName(
                updatedSupplier.getName()
        );

        existing.setContactPerson(
                updatedSupplier.getContactPerson()
        );

        existing.setEmail(
                updatedSupplier.getEmail()
        );

        existing.setPhone(
                updatedSupplier.getPhone()
        );

        existing.setAddress(
                updatedSupplier.getAddress()
        );

        existing.setCity(
                updatedSupplier.getCity()
        );

        existing.setCountry(
                updatedSupplier.getCountry()
        );

        existing.setCategory(
                updatedSupplier.getCategory()
        );

        existing.setStatus(
                updatedSupplier.getStatus()
        );

        existing.setRating(
                updatedSupplier.getRating()
        );

        existing.setUpdatedAt(
                LocalDateTime.now()
        );

        return supplierRepository.save(existing);
    }

    // DELETE
    public void deleteSupplier(Long id) {

        Supplier supplier =
                getSupplierById(id);

        supplierRepository.delete(supplier);
    }
}