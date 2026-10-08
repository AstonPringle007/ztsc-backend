package com.ztsc.backend.audit.service;

import com.ztsc.backend.audit.entity.AuditLog;
import com.ztsc.backend.audit.repository.AuditLogRepository;
import com.ztsc.backend.auth.entity.User;
import com.ztsc.backend.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public AuditLogService(
            AuditLogRepository auditLogRepository,
            UserRepository userRepository
    ) {
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // CREATE AUDIT LOG
    // =========================================================

    public AuditLog createAuditLog(
            AuditLog auditLog,
            String username
    ) {

        if (auditLog.getEventType() == null ||
                auditLog.getEventType().isBlank()) {

            throw new IllegalArgumentException(
                    "Event type is required"
            );
        }

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found: "
                                                + username
                                )
                        );

        auditLog.setUser(user);

        return auditLogRepository.save(auditLog);
    }

    // =========================================================
    // GET ALL AUDIT LOGS
    // =========================================================

    public List<AuditLog> getAllAuditLogs() {

        return auditLogRepository.findAll();
    }

    // =========================================================
    // GET AUDIT LOG BY ID
    // =========================================================

    public AuditLog getAuditLogById(Long id) {

        return auditLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Audit log not found: " + id
                        )
                );
    }

    // =========================================================
    // GET AUDIT LOGS BY USERNAME
    // =========================================================

    public List<AuditLog> getAuditLogsByUsername(
            String username
    ) {

        return auditLogRepository
                .findByUserUsername(username);
    }

    // =========================================================
    // GET AUDIT LOGS BY EVENT TYPE
    // =========================================================

    public List<AuditLog> getAuditLogsByEventType(
            String eventType
    ) {

        return auditLogRepository
                .findByEventType(
                        eventType.toUpperCase()
                );
    }

    // =========================================================
    // GET SUCCESSFUL AUDIT LOGS
    // =========================================================

    public List<AuditLog> getSuccessfulAuditLogs() {

        return auditLogRepository
                .findBySuccess(true);
    }

    // =========================================================
    // GET FAILED AUDIT LOGS
    // =========================================================

    public List<AuditLog> getFailedAuditLogs() {

        return auditLogRepository
                .findBySuccess(false);
    }

    // =========================================================
    // GET USER + EVENT TYPE
    // =========================================================

    public List<AuditLog>
    getAuditLogsByUsernameAndEventType(
            String username,
            String eventType
    ) {

        return auditLogRepository
                .findByUserUsernameAndEventType(
                        username,
                        eventType.toUpperCase()
                );
    }
}