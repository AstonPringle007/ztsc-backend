package com.ztsc.backend.Procurement.service;

import com.ztsc.backend.Procurement.entity.PurchaseRequest;
import com.ztsc.backend.Procurement.repository.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    public PurchaseRequestService(
            PurchaseRequestRepository purchaseRequestRepository
    ) {
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    // CREATE
    public PurchaseRequest createPurchaseRequest(
            PurchaseRequest request,
            String requestedBy
    ) {

        validateRequest(request);

        request.setId(null);
        request.setRequestedBy(requestedBy);
        request.setStatus("PENDING");

        LocalDateTime now = LocalDateTime.now();

        request.setCreatedAt(now);
        request.setUpdatedAt(now);

        return purchaseRequestRepository.save(request);
    }

    // GET ALL
    public List<PurchaseRequest> getAllPurchaseRequests() {

        return purchaseRequestRepository.findAll();
    }

    // GET BY ID
    public PurchaseRequest getPurchaseRequestById(Long id) {

        return purchaseRequestRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase request not found: " + id
                        )
                );
    }

    // GET CURRENT USER REQUESTS
    public List<PurchaseRequest> getRequestsByUser(
            String requestedBy
    ) {

        return purchaseRequestRepository
                .findByRequestedBy(requestedBy);
    }

    // GET BY STATUS
    public List<PurchaseRequest> getRequestsByStatus(
            String status
    ) {

        return purchaseRequestRepository
                .findByStatus(status.toUpperCase());
    }

    // APPROVE
    public PurchaseRequest approvePurchaseRequest(
            Long id,
            String approvedBy
    ) {

        PurchaseRequest request =
                getPurchaseRequestById(id);

        if (!"PENDING".equalsIgnoreCase(request.getStatus())) {

            throw new IllegalStateException(
                    "Only PENDING purchase requests can be approved"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        request.setStatus("APPROVED");
        request.setApprovedBy(approvedBy);
        request.setApprovedAt(now);
        request.setUpdatedAt(now);

        return purchaseRequestRepository.save(request);
    }

    // REJECT
    public PurchaseRequest rejectPurchaseRequest(
            Long id,
            String rejectedBy
    ) {

        PurchaseRequest request =
                getPurchaseRequestById(id);

        if (!"PENDING".equalsIgnoreCase(request.getStatus())) {

            throw new IllegalStateException(
                    "Only PENDING purchase requests can be rejected"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        request.setStatus("REJECTED");
        request.setApprovedBy(rejectedBy);
        request.setApprovedAt(now);
        request.setUpdatedAt(now);

        return purchaseRequestRepository.save(request);
    }

    // VALIDATION
    private void validateRequest(
            PurchaseRequest request
    ) {

        if (request.getTitle() == null ||
                request.getTitle().isBlank()) {

            throw new IllegalArgumentException(
                    "Title is required"
            );
        }

        if (request.getProductName() == null ||
                request.getProductName().isBlank()) {

            throw new IllegalArgumentException(
                    "Product name is required"
            );
        }

        if (request.getCategory() == null ||
                request.getCategory().isBlank()) {

            throw new IllegalArgumentException(
                    "Category is required"
            );
        }

        if (request.getQuantity() == null ||
                request.getQuantity() <= 0) {

            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        if (request.getAmount() == null ||
                request.getAmount().signum() < 0) {

            throw new IllegalArgumentException(
                    "Amount cannot be negative"
            );
        }

        if (request.getPriority() == null ||
                request.getPriority().isBlank()) {

            throw new IllegalArgumentException(
                    "Priority is required"
            );
        }
    }
}