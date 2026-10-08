package com.ztsc.backend.security.repository;

import com.ztsc.backend.security.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByDeviceId(String deviceId);

    List<Device> findByUserId(Long userId);

    List<Device> findByTrusted(boolean trusted);

    List<Device> findByActive(boolean active);

    List<Device> findByUserIdAndActive(
            Long userId,
            boolean active
    );
}