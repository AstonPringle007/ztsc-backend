package com.ztsc.backend.admin.service;

import com.ztsc.backend.admin.dto.CreateUserRequest;
import com.ztsc.backend.admin.dto.SystemSettingsResponse;
import com.ztsc.backend.admin.dto.UpdateUserRequest;
import com.ztsc.backend.admin.entity.SystemSetting;
import com.ztsc.backend.admin.repository.SystemSettingRepository;
import com.ztsc.backend.auth.entity.Role;
import com.ztsc.backend.auth.entity.User;
import com.ztsc.backend.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SystemSettingRepository systemSettingRepository;

    public AdminService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SystemSettingRepository systemSettingRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.systemSettingRepository =
                systemSettingRepository;
    }

    // =========================================================
    // USERS
    // =========================================================

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public User getUserById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + id
                        )
                );
    }

    public User createUser(
            CreateUserRequest request
    ) {

        if (request.getUsername() == null ||
                request.getUsername().isBlank()) {

            throw new IllegalArgumentException(
                    "Username is required"
            );
        }

        if (request.getEmail() == null ||
                request.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        if (request.getPassword() == null ||
                request.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "Password is required"
            );
        }

        if (request.getRole() == null) {

            throw new IllegalArgumentException(
                    "Role is required"
            );
        }

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new RuntimeException(
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        User user = new User();

        user.setUsername(
                request.getUsername().trim()
        );

        user.setEmail(
                request.getEmail().trim()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(request.getRole());
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public User updateUser(
            Long id,
            UpdateUserRequest request
    ) {

        User user = getUserById(id);

        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                !request.getEmail().equalsIgnoreCase(
                        user.getEmail())) {

            if (userRepository.existsByEmail(
                    request.getEmail())) {

                throw new RuntimeException(
                        "Email already exists"
                );
            }

            user.setEmail(
                    request.getEmail().trim()
            );
        }

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        if (request.getEnabled() != null) {
            user.setEnabled(
                    request.getEnabled()
            );
        }

        return userRepository.save(user);
    }

    public User disableUser(Long id) {

        User user = getUserById(id);

        user.setEnabled(false);

        return userRepository.save(user);
    }

    // =========================================================
    // SYSTEM SETTINGS
    // =========================================================

    public SystemSettingsResponse getSettings() {

        SystemSettingsResponse response =
                new SystemSettingsResponse();

        response.setMaintenanceMode(
                getBooleanSetting(
                        "maintenanceMode",
                        false
                )
        );

        response.setAnomalyDetectionEnabled(
                getBooleanSetting(
                        "anomalyDetectionEnabled",
                        true
                )
        );

        response.setAuditLoggingEnabled(
                getBooleanSetting(
                        "auditLoggingEnabled",
                        true
                )
        );

        response.setJwtExpirationMinutes(
                getIntegerSetting(
                        "jwtExpirationMinutes",
                        60
                )
        );

        response.setAnomalyThreshold(
                getIntegerSetting(
                        "anomalyThreshold",
                        60
                )
        );

        return response;
    }

    public SystemSettingsResponse updateSettings(
            SystemSettingsResponse request
    ) {

        if (request.getJwtExpirationMinutes() <= 0) {

            throw new IllegalArgumentException(
                    "JWT expiration must be greater than zero"
            );
        }

        if (request.getAnomalyThreshold() < 0 ||
                request.getAnomalyThreshold() > 100) {

            throw new IllegalArgumentException(
                    "Anomaly threshold must be between 0 and 100"
            );
        }

        saveSetting(
                "maintenanceMode",
                String.valueOf(
                        request.isMaintenanceMode()
                )
        );

        saveSetting(
                "anomalyDetectionEnabled",
                String.valueOf(
                        request.isAnomalyDetectionEnabled()
                )
        );

        saveSetting(
                "auditLoggingEnabled",
                String.valueOf(
                        request.isAuditLoggingEnabled()
                )
        );

        saveSetting(
                "jwtExpirationMinutes",
                String.valueOf(
                        request.getJwtExpirationMinutes()
                )
        );

        saveSetting(
                "anomalyThreshold",
                String.valueOf(
                        request.getAnomalyThreshold()
                )
        );

        return getSettings();
    }

    // =========================================================
    // SETTINGS HELPERS
    // =========================================================

    private void saveSetting(
            String key,
            String value
    ) {

        SystemSetting setting =
                systemSettingRepository
                        .findBySettingKey(key)
                        .orElseGet(() -> {

                            SystemSetting newSetting =
                                    new SystemSetting();

                            newSetting.setSettingKey(key);

                            return newSetting;
                        });

        setting.setSettingValue(value);

        systemSettingRepository.save(setting);
    }

    private boolean getBooleanSetting(
            String key,
            boolean defaultValue
    ) {

        return systemSettingRepository
                .findBySettingKey(key)
                .map(setting ->
                        Boolean.parseBoolean(
                                setting.getSettingValue()
                        )
                )
                .orElse(defaultValue);
    }

    private int getIntegerSetting(
            String key,
            int defaultValue
    ) {

        return systemSettingRepository
                .findBySettingKey(key)
                .map(setting -> {

                    try {
                        return Integer.parseInt(
                                setting.getSettingValue()
                        );
                    } catch (NumberFormatException e) {
                        return defaultValue;
                    }
                })
                .orElse(defaultValue);
    }
}