package com.ztsc.backend.security.service;

import com.ztsc.backend.auth.entity.User;
import com.ztsc.backend.auth.repository.UserRepository;
import com.ztsc.backend.security.entity.Device;
import com.ztsc.backend.security.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public DeviceService(
            DeviceRepository deviceRepository,
            UserRepository userRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    // GET ALL DEVICES
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    // GET DEVICE BY ID
    public Device getDeviceById(Long id) {

        return deviceRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Device not found: " + id
                        )
                );
    }

    // GET DEVICE BY DEVICE ID
    public Device getDeviceByDeviceId(
            String deviceId
    ) {

        return deviceRepository
                .findByDeviceId(deviceId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Device not found: " + deviceId
                        )
                );
    }

    // GET DEVICES BY USER
    public List<Device> getDevicesByUser(
            Long userId
    ) {

        return deviceRepository.findByUserId(userId);
    }

    // GET TRUSTED DEVICES
    public List<Device> getTrustedDevices() {

        return deviceRepository.findByTrusted(true);
    }

    // GET ACTIVE DEVICES
    public List<Device> getActiveDevices() {

        return deviceRepository.findByActive(true);
    }

    // REGISTER DEVICE
    public Device registerDevice(
            String deviceId,
            String deviceName,
            String ipAddress,
            String userAgent,
            String username
    ) {

        if (deviceId == null ||
                deviceId.isBlank()) {

            throw new IllegalArgumentException(
                    "Device ID is required"
            );
        }

        if (deviceRepository
                .findByDeviceId(deviceId)
                .isPresent()) {

            throw new IllegalArgumentException(
                    "Device already registered"
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

        Device device = new Device();

        device.setDeviceId(deviceId);
        device.setDeviceName(deviceName);
        device.setIpAddress(ipAddress);
        device.setUserAgent(userAgent);
        device.setUser(user);

        // Zero Trust principle:
        // New devices are NOT trusted automatically.
        device.setTrusted(false);
        device.setActive(true);

        return deviceRepository.save(device);
    }

    // TRUST DEVICE
    public Device trustDevice(Long id) {

        System.out.println("=== TRUST DEVICE START ===");
        System.out.println("Device ID: " + id);

        Device device = getDeviceById(id);

        System.out.println("Device found: " + device.getDeviceId());
        System.out.println("Active: " + device.isActive());
        System.out.println("Trusted before: " + device.isTrusted());

        if (!device.isActive()) {
            throw new IllegalStateException(
                    "Cannot trust an inactive device"
            );
        }

        device.setTrusted(true);

        System.out.println("Trusted after: " + device.isTrusted());

        Device saved = deviceRepository.save(device);

        System.out.println("Device saved successfully");
        System.out.println("=== TRUST DEVICE END ===");

        return saved;
    }

    // REVOKE DEVICE
    public Device revokeDevice(Long id) {

        Device device =
                getDeviceById(id);

        device.setTrusted(false);
        device.setActive(false);

        return deviceRepository.save(device);
    }

    // ACTIVATE DEVICE
    public Device activateDevice(Long id) {

        Device device =
                getDeviceById(id);

        device.setActive(true);

        return deviceRepository.save(device);
    }
}