package com.ztsc.backend.dashboard.dto;

public class DashboardSummaryResponse {

    private long totalPurchaseRequests;
    private long pendingPurchaseRequests;
    private long approvedPurchaseRequests;
    private long rejectedPurchaseRequests;

    private long totalSuppliers;
    private long activeSuppliers;
    private long inactiveSuppliers;

    private long totalInventoryItems;
    private long lowStockItems;
    private long outOfStockItems;

    private long totalPayments;
    private long pendingPayments;
    private long paidPayments;
    private long failedPayments;

    private long totalDevices;
    private long trustedDevices;
    private long activeDevices;

    private long totalSecurityEvents;
    private long unresolvedSecurityEvents;
    private long criticalSecurityEvents;

    public DashboardSummaryResponse() {
    }

    public long getTotalPurchaseRequests() {
        return totalPurchaseRequests;
    }

    public void setTotalPurchaseRequests(long totalPurchaseRequests) {
        this.totalPurchaseRequests = totalPurchaseRequests;
    }

    public long getPendingPurchaseRequests() {
        return pendingPurchaseRequests;
    }

    public void setPendingPurchaseRequests(long pendingPurchaseRequests) {
        this.pendingPurchaseRequests = pendingPurchaseRequests;
    }

    public long getApprovedPurchaseRequests() {
        return approvedPurchaseRequests;
    }

    public void setApprovedPurchaseRequests(long approvedPurchaseRequests) {
        this.approvedPurchaseRequests = approvedPurchaseRequests;
    }

    public long getRejectedPurchaseRequests() {
        return rejectedPurchaseRequests;
    }

    public void setRejectedPurchaseRequests(long rejectedPurchaseRequests) {
        this.rejectedPurchaseRequests = rejectedPurchaseRequests;
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

    public long getTotalInventoryItems() {
        return totalInventoryItems;
    }

    public void setTotalInventoryItems(long totalInventoryItems) {
        this.totalInventoryItems = totalInventoryItems;
    }

    public long getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(long lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public long getOutOfStockItems() {
        return outOfStockItems;
    }

    public void setOutOfStockItems(long outOfStockItems) {
        this.outOfStockItems = outOfStockItems;
    }

    public long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public long getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(long pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public long getPaidPayments() {
        return paidPayments;
    }

    public void setPaidPayments(long paidPayments) {
        this.paidPayments = paidPayments;
    }

    public long getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(long failedPayments) {
        this.failedPayments = failedPayments;
    }

    public long getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(long totalDevices) {
        this.totalDevices = totalDevices;
    }

    public long getTrustedDevices() {
        return trustedDevices;
    }

    public void setTrustedDevices(long trustedDevices) {
        this.trustedDevices = trustedDevices;
    }

    public long getActiveDevices() {
        return activeDevices;
    }

    public void setActiveDevices(long activeDevices) {
        this.activeDevices = activeDevices;
    }

    public long getTotalSecurityEvents() {
        return totalSecurityEvents;
    }

    public void setTotalSecurityEvents(long totalSecurityEvents) {
        this.totalSecurityEvents = totalSecurityEvents;
    }

    public long getUnresolvedSecurityEvents() {
        return unresolvedSecurityEvents;
    }

    public void setUnresolvedSecurityEvents(long unresolvedSecurityEvents) {
        this.unresolvedSecurityEvents = unresolvedSecurityEvents;
    }

    public long getCriticalSecurityEvents() {
        return criticalSecurityEvents;
    }

    public void setCriticalSecurityEvents(long criticalSecurityEvents) {
        this.criticalSecurityEvents = criticalSecurityEvents;
    }
}