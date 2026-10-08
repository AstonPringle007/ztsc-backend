package com.ztsc.backend.Procurement.controller;

import com.ztsc.backend.Procurement.entity.PurchaseRequest;
import com.ztsc.backend.Procurement.service.PurchaseRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procurement/purchase-requests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    public PurchaseRequestController(
            PurchaseRequestService purchaseRequestService
    ) {
        this.purchaseRequestService = purchaseRequestService;
    }

    // CREATE PURCHASE REQUEST
    @PostMapping
    public ResponseEntity<PurchaseRequest> createPurchaseRequest(
            @RequestBody PurchaseRequest request,
            Authentication authentication
    ) {

        String username = authentication.getName();

        PurchaseRequest created =
                purchaseRequestService.createPurchaseRequest(
                        request,
                        username
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // GET ALL PURCHASE REQUESTS
    @GetMapping
    public ResponseEntity<List<PurchaseRequest>>
    getAllPurchaseRequests() {

        return ResponseEntity.ok(
                purchaseRequestService
                        .getAllPurchaseRequests()
        );
    }

    // GET PURCHASE REQUEST BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequest>
    getPurchaseRequestById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                purchaseRequestService
                        .getPurchaseRequestById(id)
        );
    }

    // GET CURRENT USER REQUESTS
    @GetMapping("/my")
    public ResponseEntity<List<PurchaseRequest>>
    getMyRequests(
            Authentication authentication
    ) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                purchaseRequestService
                        .getRequestsByUser(username)
        );
    }

    // GET REQUESTS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseRequest>>
    getByStatus(
            @PathVariable String status
    ) {

        return ResponseEntity.ok(
                purchaseRequestService
                        .getRequestsByStatus(status)
        );
    }

    // APPROVE PURCHASE REQUEST
    @PatchMapping("/{id}/approve")
    public ResponseEntity<PurchaseRequest>
    approvePurchaseRequest(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String approvedBy = authentication.getName();

        PurchaseRequest approved =
                purchaseRequestService
                        .approvePurchaseRequest(
                                id,
                                approvedBy
                        );

        return ResponseEntity.ok(approved);
    }

    // REJECT PURCHASE REQUEST
    @PatchMapping("/{id}/reject")
    public ResponseEntity<PurchaseRequest>
    rejectPurchaseRequest(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String rejectedBy = authentication.getName();

        PurchaseRequest rejected =
                purchaseRequestService
                        .rejectPurchaseRequest(
                                id,
                                rejectedBy
                        );

        return ResponseEntity.ok(rejected);
    }
}