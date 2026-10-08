package com.ztsc.backend.security.repository;

import com.ztsc.backend.security.entity.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityEventRepository
        extends JpaRepository<SecurityEvent, Long> {

    List<SecurityEvent> findByUserId(Long userId);

    List<SecurityEvent> findBySeverity(String severity);

    List<SecurityEvent> findByResolved(boolean resolved);

    List<SecurityEvent> findByEventType(String eventType);

    List<SecurityEvent> findBySeverityAndResolved(
            String severity,
            boolean resolved
    );
}