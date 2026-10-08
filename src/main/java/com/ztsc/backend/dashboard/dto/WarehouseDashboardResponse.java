package com.ztsc.backend.dashboard.dto;

public class WarehouseDashboardResponse {

    private long totalItems;
    private long inStockItems;
    private long lowStockItems;
    private long outOfStockItems;

    public WarehouseDashboardResponse() {
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public long getInStockItems() {
        return inStockItems;
    }

    public void setInStockItems(long inStockItems) {
        this.inStockItems = inStockItems;
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
}