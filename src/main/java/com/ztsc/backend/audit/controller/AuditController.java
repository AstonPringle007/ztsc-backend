package com.ztsc.backend.audit.controller;

import com.ztsc.backend.audit.entity.AuditLog;
import com.ztsc.backend.audit.service.AuditLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditLogService auditLogService;

    public AuditController(
            AuditLogService auditLogService
    ) {
        this.auditLogService = auditLogService;
    }

    // =========================================================
    // GET ALL AUDIT LOGS
    // =========================================================

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLog>>
    getAllAuditLogs() {

        return ResponseEntity.ok(
                auditLogService.getAllAuditLogs()
        );
    }

    // =========================================================
    // GET AUDIT LOG BY ID
    // =========================================================

    @GetMapping("/logs/{id}")
    public ResponseEntity<AuditLog>
    getAuditLogById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogById(id)
        );
    }

    // =========================================================
    // GET LOGS BY USERNAME
    // =========================================================

    @GetMapping("/logs/user/{username}")
    public ResponseEntity<List<AuditLog>>
    getAuditLogsByUsername(
            @PathVariable String username
    ) {

        return ResponseEntity.ok(
                auditLogService
                        .getAuditLogsByUsername(username)
        );
    }

    // =========================================================
    // GET LOGS BY EVENT TYPE
    // =========================================================

    @GetMapping("/logs/event/{eventType}")
    public ResponseEntity<List<AuditLog>>
    getAuditLogsByEventType(
            @PathVariable String eventType
    ) {

        return ResponseEntity.ok(
                auditLogService
                        .getAuditLogsByEventType(eventType)
        );
    }

    // =========================================================
    // GET SUCCESSFUL LOGS
    // =========================================================

    @GetMapping("/logs/successful")
    public ResponseEntity<List<AuditLog>>
    getSuccessfulAuditLogs() {

        return ResponseEntity.ok(
                auditLogService
                        .getSuccessfulAuditLogs()
        );
    }

    // =========================================================
    // GET FAILED LOGS
    // =========================================================

    @GetMapping("/logs/failed")
    public ResponseEntity<List<AuditLog>>
    getFailedAuditLogs() {

        return ResponseEntity.ok(
                auditLogService
                        .getFailedAuditLogs()
        );
    }

    // =========================================================
    // GET USER + EVENT TYPE
    // =========================================================

    @GetMapping("/logs/user/{username}/event/{eventType}")
    public ResponseEntity<List<AuditLog>>
    getAuditLogsByUsernameAndEventType(
            @PathVariable String username,
            @PathVariable String eventType
    ) {

        return ResponseEntity.ok(
                auditLogService
                        .getAuditLogsByUsernameAndEventType(
                                username,
                                eventType
                        )
        );
    }

    // =========================================================
    // CREATE AUDIT LOG
    // =========================================================

    @PostMapping("/logs")
    public ResponseEntity<AuditLog>
    createAuditLog(
            @RequestBody AuditLog auditLog,
            Authentication authentication
    ) {

        AuditLog created =
                auditLogService.createAuditLog(
                        auditLog,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
}