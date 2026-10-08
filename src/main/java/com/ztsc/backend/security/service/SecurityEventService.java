package com.ztsc.backend.security.service;

import com.ztsc.backend.auth.entity.User;
import com.ztsc.backend.auth.repository.UserRepository;
import com.ztsc.backend.security.entity.SecurityEvent;
import com.ztsc.backend.security.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SecurityEventService {

    private final SecurityEventRepository securityEventRepository;
    private final UserRepository userRepository;

    public SecurityEventService(
            SecurityEventRepository securityEventRepository,
            UserRepository userRepository
    ) {
        this.securityEventRepository = securityEventRepository;
        this.userRepository = userRepository;
    }

    // CREATE SECURITY EVENT
    public SecurityEvent createEvent(
            SecurityEvent event,
            String username
    ) {

        if (event.getEventType() == null ||
                event.getEventType().isBlank()) {

            throw new IllegalArgumentException(
                    "Event type is required"
            );
        }

        if (event.getSeverity() == null ||
                event.getSeverity().isBlank()) {

            throw new IllegalArgumentException(
                    "Severity is required"
            );
        }

        validateSeverity(
                event.getSeverity()
        );

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found: "
                                                + username
                                )
                        );

        event.setUser(user);
        event.setResolved(false);

        return securityEventRepository.save(event);
    }

    // GET ALL EVENTS
    public List<SecurityEvent> getAllEvents() {

        return securityEventRepository.findAll();
    }

    // GET EVENT BY ID
    public SecurityEvent getEventById(Long id) {

        return securityEventRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Security event not found: "
                                        + id
                        )
                );
    }

    // GET EVENTS BY USER
    public List<SecurityEvent> getEventsByUser(
            Long userId
    ) {

        return securityEventRepository
                .findByUserId(userId);
    }

    // GET EVENTS BY SEVERITY
    public List<SecurityEvent> getEventsBySeverity(
            String severity
    ) {

        return securityEventRepository
                .findBySeverity(
                        severity.toUpperCase()
                );
    }

    // GET UNRESOLVED EVENTS
    public List<SecurityEvent> getUnresolvedEvents() {

        return securityEventRepository
                .findByResolved(false);
    }

    // GET EVENTS BY TYPE
    public List<SecurityEvent> getEventsByType(
            String eventType
    ) {

        return securityEventRepository
                .findByEventType(eventType);
    }

    // RESOLVE EVENT
    public SecurityEvent resolveEvent(Long id) {

        SecurityEvent event =
                getEventById(id);

        if (event.isResolved()) {

            throw new IllegalStateException(
                    "Security event is already resolved"
            );
        }

        event.setResolved(true);
        event.setResolvedAt(
                LocalDateTime.now()
        );

        return securityEventRepository.save(event);
    }

    // VALIDATE SEVERITY
    private void validateSeverity(
            String severity
    ) {

        String normalized =
                severity.toUpperCase();

        if (!normalized.equals("LOW") &&
                !normalized.equals("MEDIUM") &&
                !normalized.equals("HIGH") &&
                !normalized.equals("CRITICAL")) {

            throw new IllegalArgumentException(
                    "Invalid severity. Allowed values: "
                            + "LOW, MEDIUM, HIGH, CRITICAL"
            );
        }
    }
}