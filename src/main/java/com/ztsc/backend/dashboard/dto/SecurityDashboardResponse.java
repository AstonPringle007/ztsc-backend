package com.ztsc.backend.dashboard.dto;

public class SecurityDashboardResponse {

    private long totalDevices;
    private long trustedDevices;
    private long activeDevices;

    private long totalSecurityEvents;
    private long unresolvedEvents;

    private long lowSeverityEvents;
    private long mediumSeverityEvents;
    private long highSeverityEvents;
    private long criticalSeverityEvents;

    public SecurityDashboardResponse() {
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

    public long getUnresolvedEvents() {
        return unresolvedEvents;
    }

    public void setUnresolvedEvents(long unresolvedEvents) {
        this.unresolvedEvents = unresolvedEvents;
    }

    public long getLowSeverityEvents() {
        return lowSeverityEvents;
    }

    public void setLowSeverityEvents(long lowSeverityEvents) {
        this.lowSeverityEvents = lowSeverityEvents;
    }

    public long getMediumSeverityEvents() {
        return mediumSeverityEvents;
    }

    public void setMediumSeverityEvents(long mediumSeverityEvents) {
        this.mediumSeverityEvents = mediumSeverityEvents;
    }

    public long getHighSeverityEvents() {
        return highSeverityEvents;
    }

    public void setHighSeverityEvents(long highSeverityEvents) {
        this.highSeverityEvents = highSeverityEvents;
    }

    public long getCriticalSeverityEvents() {
        return criticalSeverityEvents;
    }

    public void setCriticalSeverityEvents(long criticalSeverityEvents) {
        this.criticalSeverityEvents = criticalSeverityEvents;
    }
}