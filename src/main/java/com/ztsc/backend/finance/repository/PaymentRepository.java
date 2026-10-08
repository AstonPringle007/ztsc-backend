package com.ztsc.backend.finance.repository;

import com.ztsc.backend.finance.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(
            String paymentReference
    );

    boolean existsByPaymentReference(
            String paymentReference
    );

    List<Payment> findByStatus(String status);

    List<Payment> findBySupplier(String supplier);

    List<Payment> findByPurchaseRequestId(
            Long purchaseRequestId
    );
}