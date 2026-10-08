package com.ztsc.backend.ai.service;

import com.ztsc.backend.ai.dto.AnomalyDetectionRequest;
import com.ztsc.backend.ai.dto.AnomalyDetectionResponse;
import com.ztsc.backend.security.entity.SecurityEvent;
import com.ztsc.backend.security.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnomalyDetectionService {

    private final SecurityEventRepository securityEventRepository;

    public AnomalyDetectionService(
            SecurityEventRepository securityEventRepository
    ) {
        this.securityEventRepository =
                securityEventRepository;
    }

    /**
     * Performs Zero Trust risk analysis.
     *
     * Risk score:
     *
     * 0 - 29   LOW
     * 30 - 59  MEDIUM
     * 60 - 79  HIGH
     * 80 - 100 CRITICAL
     */
    public AnomalyDetectionResponse detectAnomaly(
            AnomalyDetectionRequest request
    ) {

        int requestCount =
                safeInteger(request.getRequestCount());

        int failedLoginCount =
                safeInteger(request.getFailedLoginCount());

        double previousRisk =
                safeDouble(request.getPreviousRiskScore());

        double riskScore = 0;

        StringBuilder reasons =
                new StringBuilder();

        // -----------------------------------------------------
        // REQUEST VOLUME
        // -----------------------------------------------------

        if (requestCount > 1000) {

            riskScore += 30;

            reasons.append(
                    "Very high request volume. "
            );

        } else if (requestCount > 500) {

            riskScore += 20;

            reasons.append(
                    "High request volume. "
            );

        } else if (requestCount > 200) {

            riskScore += 10;

            reasons.append(
                    "Elevated request volume. "
            );
        }

        // -----------------------------------------------------
        // FAILED LOGIN ATTEMPTS
        // -----------------------------------------------------

        if (failedLoginCount >= 10) {

            riskScore += 30;

            reasons.append(
                    "Multiple failed login attempts detected. "
            );

        } else if (failedLoginCount >= 5) {

            riskScore += 20;

            reasons.append(
                    "Several failed login attempts detected. "
            );

        } else if (failedLoginCount >= 3) {

            riskScore += 10;

            reasons.append(
                    "Repeated failed login attempts. "
            );
        }

        // -----------------------------------------------------
        // NEW DEVICE
        // -----------------------------------------------------

        if (request.isNewDevice()) {

            riskScore += 15;

            reasons.append(
                    "Request originated from a new device. "
            );
        }

        // -----------------------------------------------------
        // UNUSUAL IP
        // -----------------------------------------------------

        if (request.isUnusualIp()) {

            riskScore += 20;

            reasons.append(
                    "Unusual IP address detected. "
            );
        }

        // -----------------------------------------------------
        // UNUSUAL TIME
        // -----------------------------------------------------

        if (request.isUnusualTime()) {

            riskScore += 10;

            reasons.append(
                    "Activity occurred during an unusual time. "
            );
        }

        // -----------------------------------------------------
        // PREVIOUS RISK
        // -----------------------------------------------------

        if (previousRisk >= 80) {

            riskScore += 15;

            reasons.append(
                    "Previous activity already had critical risk. "
            );

        } else if (previousRisk >= 60) {

            riskScore += 10;

            reasons.append(
                    "Previous activity had high risk. "
            );

        } else if (previousRisk >= 40) {

            riskScore += 5;

            reasons.append(
                    "Previous activity had elevated risk. "
            );
        }

        // -----------------------------------------------------
        // NORMALIZE
        // -----------------------------------------------------

        riskScore =
                Math.min(100, riskScore);

        String riskLevel =
                determineRiskLevel(riskScore);

        boolean anomalous =
                riskScore >= 60;

        String recommendation =
                determineRecommendation(
                        riskScore
                );

        String reason =
                reasons.length() == 0
                        ? "No significant anomaly indicators detected."
                        : reasons.toString();

        return new AnomalyDetectionResponse(
                riskScore,
                riskLevel,
                anomalous,
                recommendation,
                reason
        );
    }

    // ---------------------------------------------------------
    // ANALYZE EXISTING SECURITY EVENT
    // ---------------------------------------------------------

    public AnomalyDetectionResponse
    analyzeSecurityEvent(Long eventId) {

        SecurityEvent event =
                securityEventRepository
                        .findById(eventId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Security event not found: "
                                                + eventId
                                )
                        );

        AnomalyDetectionRequest request =
                new AnomalyDetectionRequest();

        request.setRequestCount(
                event.getRequestCount()
        );

        request.setFailedLoginCount(
                event.getFailedLoginCount()
        );

        request.setPreviousRiskScore(
                event.getRiskScore()
        );

        AnomalyDetectionResponse response =
                detectAnomaly(request);

        // Update event with calculated risk score
        event.setRiskScore(
                response.getRiskScore()
        );

        if (response.getRiskLevel()
                .equals("CRITICAL")) {

            event.setSeverity("CRITICAL");

        } else if (response.getRiskLevel()
                .equals("HIGH")) {

            event.setSeverity("HIGH");

        } else if (response.getRiskLevel()
                .equals("MEDIUM")) {

            event.setSeverity("MEDIUM");

        } else {

            event.setSeverity("LOW");
        }

        securityEventRepository.save(event);

        return response;
    }

    // ---------------------------------------------------------
    // RISK LEVEL
    // ---------------------------------------------------------

    private String determineRiskLevel(
            double score
    ) {

        if (score >= 80) {
            return "CRITICAL";
        }

        if (score >= 60) {
            return "HIGH";
        }

        if (score >= 30) {
            return "MEDIUM";
        }

        return "LOW";
    }

    // ---------------------------------------------------------
    // RECOMMENDATION
    // ---------------------------------------------------------

    private String determineRecommendation(
            double score
    ) {

        if (score >= 80) {

            return "BLOCK ACCESS AND INVESTIGATE IMMEDIATELY";
        }

        if (score >= 60) {

            return "REVOKE OR CHALLENGE ACCESS AND INVESTIGATE";
        }

        if (score >= 30) {

            return "MONITOR ACTIVITY AND REQUIRE ADDITIONAL VERIFICATION";
        }

        return "ALLOW ACCESS AND CONTINUE MONITORING";
    }

    private int safeInteger(Integer value) {

        return value == null ? 0 : value;
    }

    private double safeDouble(Double value) {

        return value == null ? 0 : value;
    }
}