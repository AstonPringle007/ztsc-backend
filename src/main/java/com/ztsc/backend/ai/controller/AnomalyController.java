package com.ztsc.backend.ai.controller;

import com.ztsc.backend.ai.dto.AnomalyDetectionRequest;
import com.ztsc.backend.ai.dto.AnomalyDetectionResponse;
import com.ztsc.backend.ai.service.AnomalyDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security/anomaly")
public class AnomalyController {

    private final AnomalyDetectionService
            anomalyDetectionService;

    public AnomalyController(
            AnomalyDetectionService anomalyDetectionService
    ) {
        this.anomalyDetectionService =
                anomalyDetectionService;
    }

    // ---------------------------------------------------------
    // REAL-TIME ANOMALY DETECTION
    // ---------------------------------------------------------

    @PostMapping("/detect")
    public ResponseEntity<AnomalyDetectionResponse>
    detectAnomaly(
            @RequestBody AnomalyDetectionRequest request
    ) {

        return ResponseEntity.ok(
                anomalyDetectionService
                        .detectAnomaly(request)
        );
    }

    // ---------------------------------------------------------
    // ANALYZE EXISTING SECURITY EVENT
    // ---------------------------------------------------------

    @PostMapping("/event/{eventId}")
    public ResponseEntity<AnomalyDetectionResponse>
    analyzeSecurityEvent(
            @PathVariable Long eventId
    ) {

        return ResponseEntity.ok(
                anomalyDetectionService
                        .analyzeSecurityEvent(eventId)
        );
    }

    // ---------------------------------------------------------
    // GET RISK SCORE FOR SECURITY EVENT
    // ---------------------------------------------------------

    @GetMapping("/risk-score/{eventId}")
    public ResponseEntity<AnomalyDetectionResponse>
    getRiskScore(
            @PathVariable Long eventId
    ) {

        return ResponseEntity.ok(
                anomalyDetectionService
                        .analyzeSecurityEvent(eventId)
        );
    }
}