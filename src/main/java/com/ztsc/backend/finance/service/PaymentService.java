package com.ztsc.backend.finance.service;

import com.ztsc.backend.finance.entity.Payment;
import com.ztsc.backend.finance.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(
            PaymentRepository paymentRepository
    ) {
        this.paymentRepository = paymentRepository;
    }

    // CREATE PAYMENT
    public Payment createPayment(
            Payment payment,
            String processedBy
    ) {

        validatePayment(payment);

        if (paymentRepository.existsByPaymentReference(
                payment.getPaymentReference()
        )) {

            throw new IllegalArgumentException(
                    "Payment reference already exists"
            );
        }

        payment.setId(null);

        if (payment.getCurrency() == null ||
                payment.getCurrency().isBlank()) {

            payment.setCurrency("INR");
        }

        if (payment.getStatus() == null ||
                payment.getStatus().isBlank()) {

            payment.setStatus("PENDING");
        }

        payment.setProcessedBy(processedBy);

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(
                    LocalDateTime.now()
            );
        }

        LocalDateTime now = LocalDateTime.now();

        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        return paymentRepository.save(payment);
    }

    // GET ALL PAYMENTS
    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }

    // GET PAYMENT BY ID
    public Payment getPaymentById(Long id) {

        return paymentRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found: " + id
                        )
                );
    }

    // GET PAYMENTS BY STATUS
    public List<Payment> getPaymentsByStatus(
            String status
    ) {

        return paymentRepository.findByStatus(
                status.toUpperCase()
        );
    }

    // GET PAYMENTS BY SUPPLIER
    public List<Payment> getPaymentsBySupplier(
            String supplier
    ) {

        return paymentRepository.findBySupplier(
                supplier
        );
    }

    // GET PAYMENTS BY PURCHASE REQUEST
    public List<Payment> getPaymentsByPurchaseRequest(
            Long purchaseRequestId
    ) {

        return paymentRepository
                .findByPurchaseRequestId(
                        purchaseRequestId
                );
    }

    // UPDATE PAYMENT
    public Payment updatePayment(
            Long id,
            Payment updatedPayment
    ) {

        Payment existing =
                getPaymentById(id);

        validatePaymentForUpdate(updatedPayment);

        existing.setSupplier(
                updatedPayment.getSupplier()
        );

        existing.setAmount(
                updatedPayment.getAmount()
        );

        existing.setCurrency(
                updatedPayment.getCurrency()
        );

        existing.setPaymentMethod(
                updatedPayment.getPaymentMethod()
        );

        existing.setInvoiceNumber(
                updatedPayment.getInvoiceNumber()
        );

        existing.setDescription(
                updatedPayment.getDescription()
        );

        existing.setPaymentDate(
                updatedPayment.getPaymentDate()
        );

        existing.setUpdatedAt(
                LocalDateTime.now()
        );

        return paymentRepository.save(existing);
    }

    // UPDATE PAYMENT STATUS
    public Payment updatePaymentStatus(
            Long id,
            String status,
            String processedBy
    ) {

        Payment payment =
                getPaymentById(id);

        String normalizedStatus =
                status.toUpperCase();

        validateStatus(normalizedStatus);

        payment.setStatus(normalizedStatus);
        payment.setProcessedBy(processedBy);
        payment.setUpdatedAt(
                LocalDateTime.now()
        );

        if ("PAID".equals(normalizedStatus)) {

            payment.setPaymentDate(
                    LocalDateTime.now()
            );
        }

        return paymentRepository.save(payment);
    }

    // DELETE PAYMENT
    public void deletePayment(Long id) {

        Payment payment =
                getPaymentById(id);

        paymentRepository.delete(payment);
    }

    // VALIDATION
    private void validatePayment(
            Payment payment
    ) {

        if (payment.getPaymentReference() == null ||
                payment.getPaymentReference().isBlank()) {

            throw new IllegalArgumentException(
                    "Payment reference is required"
            );
        }

        if (payment.getSupplier() == null ||
                payment.getSupplier().isBlank()) {

            throw new IllegalArgumentException(
                    "Supplier is required"
            );
        }

        if (payment.getAmount() == null ||
                payment.getAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        if (payment.getPaymentMethod() == null ||
                payment.getPaymentMethod().isBlank()) {

            throw new IllegalArgumentException(
                    "Payment method is required"
            );
        }

        if (payment.getCurrency() != null &&
                payment.getCurrency().isBlank()) {

            throw new IllegalArgumentException(
                    "Currency cannot be blank"
            );
        }

        if (payment.getStatus() != null &&
                !payment.getStatus().isBlank()) {

            validateStatus(
                    payment.getStatus().toUpperCase()
            );
        }
    }

    private void validatePaymentForUpdate(
            Payment payment
    ) {

        if (payment.getSupplier() == null ||
                payment.getSupplier().isBlank()) {

            throw new IllegalArgumentException(
                    "Supplier is required"
            );
        }

        if (payment.getAmount() == null ||
                payment.getAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        if (payment.getPaymentMethod() == null ||
                payment.getPaymentMethod().isBlank()) {

            throw new IllegalArgumentException(
                    "Payment method is required"
            );
        }
    }

    private void validateStatus(
            String status
    ) {

        if (!status.equals("PENDING") &&
                !status.equals("PROCESSING") &&
                !status.equals("PAID") &&
                !status.equals("FAILED") &&
                !status.equals("CANCELLED")) {

            throw new IllegalArgumentException(
                    "Invalid payment status. Allowed values: "
                            + "PENDING, PROCESSING, PAID, FAILED, CANCELLED"
            );
        }
    }
}