package com.ztsc.backend.Procurement.repository;

import com.ztsc.backend.Procurement.entity.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRequestRepository
        extends JpaRepository<PurchaseRequest, Long> {

    List<PurchaseRequest> findByRequestedBy(String requestedBy);

    List<PurchaseRequest> findByStatus(String status);
}