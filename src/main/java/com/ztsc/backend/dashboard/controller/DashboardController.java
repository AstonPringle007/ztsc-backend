package com.ztsc.backend.dashboard.controller;

import com.ztsc.backend.dashboard.dto.DashboardSummaryResponse;
import com.ztsc.backend.dashboard.dto.FinanceDashboardResponse;
import com.ztsc.backend.dashboard.dto.ProcurementDashboardResponse;
import com.ztsc.backend.dashboard.dto.SecurityDashboardResponse;
import com.ztsc.backend.dashboard.dto.SupplierDashboardResponse;
import com.ztsc.backend.dashboard.dto.WarehouseDashboardResponse;
import com.ztsc.backend.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    // =========================================================
    // OVERALL SUMMARY
    // =========================================================

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse>
    getSummary() {

        return ResponseEntity.ok(
                dashboardService.getSummary()
        );
    }

    // =========================================================
    // PROCUREMENT
    // =========================================================

    @GetMapping("/procurement")
    public ResponseEntity<ProcurementDashboardResponse>
    getProcurementDashboard() {

        return ResponseEntity.ok(
                dashboardService
                        .getProcurementDashboard()
        );
    }

    // =========================================================
    // SUPPLIERS
    // =========================================================

    @GetMapping("/suppliers")
    public ResponseEntity<SupplierDashboardResponse>
    getSupplierDashboard() {

        return ResponseEntity.ok(
                dashboardService
                        .getSupplierDashboard()
        );
    }

    // =========================================================
    // WAREHOUSE
    // =========================================================

    @GetMapping("/warehouse")
    public ResponseEntity<WarehouseDashboardResponse>
    getWarehouseDashboard() {

        return ResponseEntity.ok(
                dashboardService
                        .getWarehouseDashboard()
        );
    }

    // =========================================================
    // FINANCE
    // =========================================================

    @GetMapping("/finance")
    public ResponseEntity<FinanceDashboardResponse>
    getFinanceDashboard() {

        return ResponseEntity.ok(
                dashboardService
                        .getFinanceDashboard()
        );
    }

    // =========================================================
    // SECURITY
    // =========================================================

    @GetMapping("/security")
    public ResponseEntity<SecurityDashboardResponse>
    getSecurityDashboard() {

        return ResponseEntity.ok(
                dashboardService
                        .getSecurityDashboard()
        );
    }
}