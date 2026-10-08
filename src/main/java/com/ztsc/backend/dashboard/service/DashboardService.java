package com.ztsc.backend.dashboard.service;

import com.ztsc.backend.Procurement.repository.PurchaseRequestRepository;
import com.ztsc.backend.dashboard.dto.DashboardSummaryResponse;
import com.ztsc.backend.dashboard.dto.FinanceDashboardResponse;
import com.ztsc.backend.dashboard.dto.ProcurementDashboardResponse;
import com.ztsc.backend.dashboard.dto.SecurityDashboardResponse;
import com.ztsc.backend.dashboard.dto.SupplierDashboardResponse;
import com.ztsc.backend.dashboard.dto.WarehouseDashboardResponse;
import com.ztsc.backend.finance.repository.PaymentRepository;
import com.ztsc.backend.security.repository.DeviceRepository;
import com.ztsc.backend.security.repository.SecurityEventRepository;
import com.ztsc.backend.supplier.repository.SupplierRepository;
import com.ztsc.backend.warehouse.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final SupplierRepository supplierRepository;
    private final InventoryRepository inventoryRepository;
    private final PaymentRepository paymentRepository;
    private final DeviceRepository deviceRepository;
    private final SecurityEventRepository securityEventRepository;

    public DashboardService(
            PurchaseRequestRepository purchaseRequestRepository,
            SupplierRepository supplierRepository,
            InventoryRepository inventoryRepository,
            PaymentRepository paymentRepository,
            DeviceRepository deviceRepository,
            SecurityEventRepository securityEventRepository
    ) {
        this.purchaseRequestRepository =
                purchaseRequestRepository;

        this.supplierRepository =
                supplierRepository;

        this.inventoryRepository =
                inventoryRepository;

        this.paymentRepository =
                paymentRepository;

        this.deviceRepository =
                deviceRepository;

        this.securityEventRepository =
                securityEventRepository;
    }

    // =========================================================
    // OVERALL SUMMARY
    // =========================================================

    public DashboardSummaryResponse getSummary() {

        DashboardSummaryResponse response =
                new DashboardSummaryResponse();

        // Procurement
        response.setTotalPurchaseRequests(
                purchaseRequestRepository.count()
        );

        response.setPendingPurchaseRequests(
                purchaseRequestRepository
                        .findByStatus("PENDING")
                        .size()
        );

        response.setApprovedPurchaseRequests(
                purchaseRequestRepository
                        .findByStatus("APPROVED")
                        .size()
        );

        response.setRejectedPurchaseRequests(
                purchaseRequestRepository
                        .findByStatus("REJECTED")
                        .size()
        );

        // Suppliers
        response.setTotalSuppliers(
                supplierRepository.count()
        );

        response.setActiveSuppliers(
                supplierRepository
                        .findByStatus("ACTIVE")
                        .size()
        );

        response.setInactiveSuppliers(
                supplierRepository
                        .findByStatus("INACTIVE")
                        .size()
        );

        // Warehouse
        response.setTotalInventoryItems(
                inventoryRepository.count()
        );

        response.setLowStockItems(
                inventoryRepository
                        .findByStatus("LOW_STOCK")
                        .size()
        );

        response.setOutOfStockItems(
                inventoryRepository
                        .findByStatus("OUT_OF_STOCK")
                        .size()
        );

        // Finance
        response.setTotalPayments(
                paymentRepository.count()
        );

        response.setPendingPayments(
                paymentRepository
                        .findByStatus("PENDING")
                        .size()
        );

        response.setPaidPayments(
                paymentRepository
                        .findByStatus("PAID")
                        .size()
        );

        response.setFailedPayments(
                paymentRepository
                        .findByStatus("FAILED")
                        .size()
        );

        // Security
        response.setTotalDevices(
                deviceRepository.count()
        );

        response.setTrustedDevices(
                deviceRepository
                        .findByTrusted(true)
                        .size()
        );

        response.setActiveDevices(
                deviceRepository
                        .findByActive(true)
                        .size()
        );

        response.setTotalSecurityEvents(
                securityEventRepository.count()
        );

        response.setUnresolvedSecurityEvents(
                securityEventRepository
                        .findByResolved(false)
                        .size()
        );

        response.setCriticalSecurityEvents(
                securityEventRepository
                        .findBySeverity("CRITICAL")
                        .size()
        );

        return response;
    }

    // =========================================================
    // PROCUREMENT DASHBOARD
    // =========================================================

    public ProcurementDashboardResponse
    getProcurementDashboard() {

        ProcurementDashboardResponse response =
                new ProcurementDashboardResponse();

        response.setTotalRequests(
                purchaseRequestRepository.count()
        );

        response.setPendingRequests(
                purchaseRequestRepository
                        .findByStatus("PENDING")
                        .size()
        );

        response.setApprovedRequests(
                purchaseRequestRepository
                        .findByStatus("APPROVED")
                        .size()
        );

        response.setRejectedRequests(
                purchaseRequestRepository
                        .findByStatus("REJECTED")
                        .size()
        );

        return response;
    }

    // =========================================================
    // SUPPLIER DASHBOARD
    // =========================================================

    public SupplierDashboardResponse
    getSupplierDashboard() {

        SupplierDashboardResponse response =
                new SupplierDashboardResponse();

        response.setTotalSuppliers(
                supplierRepository.count()
        );

        response.setActiveSuppliers(
                supplierRepository
                        .findByStatus("ACTIVE")
                        .size()
        );

        response.setInactiveSuppliers(
                supplierRepository
                        .findByStatus("INACTIVE")
                        .size()
        );

        return response;
    }

    // =========================================================
    // WAREHOUSE DASHBOARD
    // =========================================================

    public WarehouseDashboardResponse
    getWarehouseDashboard() {

        WarehouseDashboardResponse response =
                new WarehouseDashboardResponse();

        response.setTotalItems(
                inventoryRepository.count()
        );

        response.setInStockItems(
                inventoryRepository
                        .findByStatus("IN_STOCK")
                        .size()
        );

        response.setLowStockItems(
                inventoryRepository
                        .findByStatus("LOW_STOCK")
                        .size()
        );

        response.setOutOfStockItems(
                inventoryRepository
                        .findByStatus("OUT_OF_STOCK")
                        .size()
        );

        return response;
    }

    // =========================================================
    // FINANCE DASHBOARD
    // =========================================================

    public FinanceDashboardResponse
    getFinanceDashboard() {

        FinanceDashboardResponse response =
                new FinanceDashboardResponse();

        response.setTotalPayments(
                paymentRepository.count()
        );

        response.setPendingPayments(
                paymentRepository
                        .findByStatus("PENDING")
                        .size()
        );

        response.setProcessingPayments(
                paymentRepository
                        .findByStatus("PROCESSING")
                        .size()
        );

        response.setPaidPayments(
                paymentRepository
                        .findByStatus("PAID")
                        .size()
        );

        response.setFailedPayments(
                paymentRepository
                        .findByStatus("FAILED")
                        .size()
        );

        response.setCancelledPayments(
                paymentRepository
                        .findByStatus("CANCELLED")
                        .size()
        );

        return response;
    }

    // =========================================================
    // SECURITY DASHBOARD
    // =========================================================

    public SecurityDashboardResponse
    getSecurityDashboard() {

        SecurityDashboardResponse response =
                new SecurityDashboardResponse();

        // Devices
        response.setTotalDevices(
                deviceRepository.count()
        );

        response.setTrustedDevices(
                deviceRepository
                        .findByTrusted(true)
                        .size()
        );

        response.setActiveDevices(
                deviceRepository
                        .findByActive(true)
                        .size()
        );

        // Security events
        response.setTotalSecurityEvents(
                securityEventRepository.count()
        );

        response.setUnresolvedEvents(
                securityEventRepository
                        .findByResolved(false)
                        .size()
        );

        response.setLowSeverityEvents(
                securityEventRepository
                        .findBySeverity("LOW")
                        .size()
        );

        response.setMediumSeverityEvents(
                securityEventRepository
                        .findBySeverity("MEDIUM")
                        .size()
        );

        response.setHighSeverityEvents(
                securityEventRepository
                        .findBySeverity("HIGH")
                        .size()
        );

        response.setCriticalSeverityEvents(
                securityEventRepository
                        .findBySeverity("CRITICAL")
                        .size()
        );

        return response;
    }
}