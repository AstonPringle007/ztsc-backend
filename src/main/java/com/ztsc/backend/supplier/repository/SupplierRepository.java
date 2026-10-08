package com.ztsc.backend.supplier.repository;

import com.ztsc.backend.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository
        extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findBySupplierCode(String supplierCode);

    boolean existsBySupplierCode(String supplierCode);

    boolean existsByEmail(String email);

    List<Supplier> findByStatus(String status);

    List<Supplier> findByCategory(String category);
}