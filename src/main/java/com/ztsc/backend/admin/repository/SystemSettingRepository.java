package com.ztsc.backend.admin.repository;

import com.ztsc.backend.admin.entity.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemSettingRepository
        extends JpaRepository<SystemSetting, Long> {

    Optional<SystemSetting> findBySettingKey(
            String settingKey
    );
}