package com.ztsc.backend.dashboard.dto;

public class SupplierDashboardResponse {

    private long totalSuppliers;
    private long activeSuppliers;
    private long inactiveSuppliers;

    public SupplierDashboardResponse() {
    }

    public long getTotalSuppliers() {
        return totalSuppliers;
    }

    public void setTotalSuppliers(long totalSuppliers) {
        this.totalSuppliers = totalSuppliers;
    }

    public long getActiveSuppliers() {
        return activeSuppliers;
    }

    public void setActiveSuppliers(long activeSuppliers) {
        this.activeSuppliers = activeSuppliers;
    }

    public long getInactiveSuppliers() {
        return inactiveSuppliers;
    }

    public void setInactiveSuppliers(long inactiveSuppliers) {
        this.inactiveSuppliers = inactiveSuppliers;
    }
}