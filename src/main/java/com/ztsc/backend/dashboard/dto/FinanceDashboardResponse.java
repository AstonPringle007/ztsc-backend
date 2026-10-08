package com.ztsc.backend.dashboard.dto;

public class FinanceDashboardResponse {

    private long totalPayments;
    private long pendingPayments;
    private long processingPayments;
    private long paidPayments;
    private long failedPayments;
    private long cancelledPayments;

    public FinanceDashboardResponse() {
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

    public long getProcessingPayments() {
        return processingPayments;
    }

    public void setProcessingPayments(long processingPayments) {
        this.processingPayments = processingPayments;
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

    public long getCancelledPayments() {
        return cancelledPayments;
    }

    public void setCancelledPayments(long cancelledPayments) {
        this.cancelledPayments = cancelledPayments;
    }
}