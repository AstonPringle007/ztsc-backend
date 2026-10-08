package com.ztsc.backend.audit.repository;

import com.ztsc.backend.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserUsername(
            String username
    );

    List<AuditLog> findByEventType(
            String eventType
    );

    List<AuditLog> findBySuccess(
            boolean success
    );

    List<AuditLog> findByUserUsernameAndEventType(
            String username,
            String eventType
    );
}