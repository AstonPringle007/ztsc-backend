package com.ztsc.backend.finance.controller;

import com.ztsc.backend.finance.entity.Payment;
import com.ztsc.backend.finance.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService
    ) {
        this.paymentService = paymentService;
    }

    // FINANCE DASHBOARD
    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {

        return ResponseEntity.ok(
                "Welcome to Finance Dashboard"
        );
    }

    // GET ALL PAYMENTS
    @GetMapping("/payments")
    public ResponseEntity<List<Payment>>
    getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments()
        );
    }

    // GET PAYMENT BY ID
    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment>
    getPaymentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id)
        );
    }

    // GET PAYMENTS BY STATUS
    @GetMapping("/payments/status/{status}")
    public ResponseEntity<List<Payment>>
    getPaymentsByStatus(
            @PathVariable String status
    ) {

        return ResponseEntity.ok(
                paymentService
                        .getPaymentsByStatus(status)
        );
    }

    // GET PAYMENTS BY SUPPLIER
    @GetMapping("/payments/supplier/{supplier}")
    public ResponseEntity<List<Payment>>
    getPaymentsBySupplier(
            @PathVariable String supplier
    ) {

        return ResponseEntity.ok(
                paymentService
                        .getPaymentsBySupplier(supplier)
        );
    }

    // GET PAYMENTS BY PURCHASE REQUEST
    @GetMapping("/payments/purchase-request/{purchaseRequestId}")
    public ResponseEntity<List<Payment>>
    getPaymentsByPurchaseRequest(
            @PathVariable Long purchaseRequestId
    ) {

        return ResponseEntity.ok(
                paymentService
                        .getPaymentsByPurchaseRequest(
                                purchaseRequestId
                        )
        );
    }

    // CREATE PAYMENT
    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(
            @RequestBody Payment payment,
            Authentication authentication
    ) {

        String username =
                authentication.getName();

        Payment created =
                paymentService.createPayment(
                        payment,
                        username
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // UPDATE PAYMENT
    @PutMapping("/payments/{id}")
    public ResponseEntity<Payment>
    updatePayment(
            @PathVariable Long id,
            @RequestBody Payment payment
    ) {

        return ResponseEntity.ok(
                paymentService.updatePayment(
                        id,
                        payment
                )
        );
    }

    // UPDATE PAYMENT STATUS
    @PatchMapping("/payments/{id}/status")
    public ResponseEntity<Payment>
    updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String status,
            Authentication authentication
    ) {

        String username =
                authentication.getName();

        return ResponseEntity.ok(
                paymentService.updatePaymentStatus(
                        id,
                        status,
                        username
                )
        );
    }

    // DELETE PAYMENT
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void>
    deletePayment(
            @PathVariable Long id
    ) {

        paymentService.deletePayment(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}