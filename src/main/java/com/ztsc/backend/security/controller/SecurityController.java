package com.ztsc.backend.security.controller;

import com.ztsc.backend.security.entity.Device;
import com.ztsc.backend.security.entity.SecurityEvent;
import com.ztsc.backend.security.service.DeviceService;
import com.ztsc.backend.security.service.SecurityEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final DeviceService deviceService;
    private final SecurityEventService securityEventService;

    public SecurityController(
            DeviceService deviceService,
            SecurityEventService securityEventService
    ) {
        this.deviceService = deviceService;
        this.securityEventService = securityEventService;
    }

    // =========================================================
    // DEVICE APIs
    // =========================================================

    // GET ALL DEVICES
    @GetMapping("/devices")
    public ResponseEntity<List<Device>>
    getAllDevices() {

        return ResponseEntity.ok(
                deviceService.getAllDevices()
        );
    }

    // GET DEVICE BY ID
    @GetMapping("/devices/{id}")
    public ResponseEntity<Device>
    getDeviceById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                deviceService.getDeviceById(id)
        );
    }

    // GET TRUSTED DEVICES
    @GetMapping("/devices/trusted")
    public ResponseEntity<List<Device>>
    getTrustedDevices() {

        return ResponseEntity.ok(
                deviceService.getTrustedDevices()
        );
    }

    // GET ACTIVE DEVICES
    @GetMapping("/devices/active")
    public ResponseEntity<List<Device>>
    getActiveDevices() {

        return ResponseEntity.ok(
                deviceService.getActiveDevices()
        );
    }

    // REGISTER DEVICE
    @PostMapping("/devices")
    public ResponseEntity<Device>
    registerDevice(
            @RequestParam String deviceId,
            @RequestParam(required = false) String deviceName,
            @RequestParam(required = false) String ipAddress,
            @RequestParam(required = false) String userAgent,
            Authentication authentication
    ) {

        Device device =
                deviceService.registerDevice(
                        deviceId,
                        deviceName,
                        ipAddress,
                        userAgent,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(device);
    }

    // TRUST DEVICE
    @PatchMapping("/devices/{id}/trust")
    public ResponseEntity<Device>
    trustDevice(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                deviceService.trustDevice(id)
        );
    }

    // REVOKE DEVICE
    @PatchMapping("/devices/{id}/revoke")
    public ResponseEntity<Device>
    revokeDevice(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                deviceService.revokeDevice(id)
        );
    }

    // ACTIVATE DEVICE
    @PatchMapping("/devices/{id}/activate")
    public ResponseEntity<Device>
    activateDevice(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                deviceService.activateDevice(id)
        );
    }

    // =========================================================
    // SECURITY EVENT APIs
    // =========================================================

    // GET ALL EVENTS
    @GetMapping("/events")
    public ResponseEntity<List<SecurityEvent>>
    getAllEvents() {

        return ResponseEntity.ok(
                securityEventService.getAllEvents()
        );
    }

    // GET EVENT BY ID
    @GetMapping("/events/{id}")
    public ResponseEntity<SecurityEvent>
    getEventById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                securityEventService.getEventById(id)
        );
    }

    // GET EVENTS BY USER
    @GetMapping("/events/user/{userId}")
    public ResponseEntity<List<SecurityEvent>>
    getEventsByUser(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                securityEventService
                        .getEventsByUser(userId)
        );
    }

    // GET EVENTS BY SEVERITY
    @GetMapping("/events/severity/{severity}")
    public ResponseEntity<List<SecurityEvent>>
    getEventsBySeverity(
            @PathVariable String severity
    ) {

        return ResponseEntity.ok(
                securityEventService
                        .getEventsBySeverity(severity)
        );
    }

    // GET UNRESOLVED EVENTS
    @GetMapping("/events/unresolved")
    public ResponseEntity<List<SecurityEvent>>
    getUnresolvedEvents() {

        return ResponseEntity.ok(
                securityEventService
                        .getUnresolvedEvents()
        );
    }

    // GET EVENTS BY TYPE
    @GetMapping("/events/type/{eventType}")
    public ResponseEntity<List<SecurityEvent>>
    getEventsByType(
            @PathVariable String eventType
    ) {

        return ResponseEntity.ok(
                securityEventService
                        .getEventsByType(eventType)
        );
    }

    // CREATE SECURITY EVENT
    @PostMapping("/events")
    public ResponseEntity<SecurityEvent>
    createEvent(
            @RequestBody SecurityEvent event,
            Authentication authentication
    ) {

        SecurityEvent created =
                securityEventService.createEvent(
                        event,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // RESOLVE SECURITY EVENT
    @PatchMapping("/events/{id}/resolve")
    public ResponseEntity<SecurityEvent>
    resolveEvent(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                securityEventService
                        .resolveEvent(id)
        );
    }
}