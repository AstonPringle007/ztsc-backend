package com.ztsc.backend.admin.dto;

public class SystemSettingsResponse {

    private boolean maintenanceMode;
    private boolean anomalyDetectionEnabled;
    private boolean auditLoggingEnabled;
    private int jwtExpirationMinutes;
    private int anomalyThreshold;

    public SystemSettingsResponse() {
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public void setMaintenanceMode(boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }

    public boolean isAnomalyDetectionEnabled() {
        return anomalyDetectionEnabled;
    }

    public void setAnomalyDetectionEnabled(
            boolean anomalyDetectionEnabled) {
        this.anomalyDetectionEnabled =
                anomalyDetectionEnabled;
    }

    public boolean isAuditLoggingEnabled() {
        return auditLoggingEnabled;
    }

    public void setAuditLoggingEnabled(
            boolean auditLoggingEnabled) {
        this.auditLoggingEnabled =
                auditLoggingEnabled;
    }

    public int getJwtExpirationMinutes() {
        return jwtExpirationMinutes;
    }

    public void setJwtExpirationMinutes(
            int jwtExpirationMinutes) {
        this.jwtExpirationMinutes =
                jwtExpirationMinutes;
    }

    public int getAnomalyThreshold() {
        return anomalyThreshold;
    }

    public void setAnomalyThreshold(
            int anomalyThreshold) {
        this.anomalyThreshold = anomalyThreshold;
    }
}