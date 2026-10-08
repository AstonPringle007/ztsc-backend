package com.ztsc.backend.ai.dto;

public class AnomalyDetectionResponse {

    private double riskScore;

    private String riskLevel;

    private boolean anomalous;

    private String recommendation;

    private String reason;

    public AnomalyDetectionResponse() {
    }

    public AnomalyDetectionResponse(
            double riskScore,
            String riskLevel,
            boolean anomalous,
            String recommendation,
            String reason
    ) {
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.anomalous = anomalous;
        this.recommendation = recommendation;
        this.reason = reason;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public boolean isAnomalous() {
        return anomalous;
    }

    public void setAnomalous(boolean anomalous) {
        this.anomalous = anomalous;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}