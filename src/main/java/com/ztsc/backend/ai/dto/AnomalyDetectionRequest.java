package com.ztsc.backend.ai.dto;

public class AnomalyDetectionRequest {

    private Integer requestCount;

    private Integer failedLoginCount;

    private Double previousRiskScore;

    private boolean newDevice;

    private boolean unusualIp;

    private boolean unusualTime;

    public AnomalyDetectionRequest() {
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public Double getPreviousRiskScore() {
        return previousRiskScore;
    }

    public void setPreviousRiskScore(Double previousRiskScore) {
        this.previousRiskScore = previousRiskScore;
    }

    public boolean isNewDevice() {
        return newDevice;
    }

    public void setNewDevice(boolean newDevice) {
        this.newDevice = newDevice;
    }

    public boolean isUnusualIp() {
        return unusualIp;
    }

    public void setUnusualIp(boolean unusualIp) {
        this.unusualIp = unusualIp;
    }

    public boolean isUnusualTime() {
        return unusualTime;
    }

    public void setUnusualTime(boolean unusualTime) {
        this.unusualTime = unusualTime;
    }
}