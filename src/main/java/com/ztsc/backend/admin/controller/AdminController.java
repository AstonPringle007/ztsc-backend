package com.ztsc.backend.admin.controller;

import com.ztsc.backend.admin.dto.CreateUserRequest;
import com.ztsc.backend.admin.dto.SystemSettingsResponse;
import com.ztsc.backend.admin.dto.UpdateUserRequest;
import com.ztsc.backend.admin.dto.UserResponse;
import com.ztsc.backend.admin.service.AdminService;
import com.ztsc.backend.auth.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(
            AdminService adminService
    ) {
        this.adminService = adminService;
    }

    // =========================================================
    // GET ALL USERS
    // =========================================================

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users =
                adminService.getAllUsers()
                        .stream()
                        .map(UserResponse::fromUser)
                        .toList();

        return ResponseEntity.ok(users);
    }

    // =========================================================
    // GET USER BY ID
    // =========================================================

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {

        User user =
                adminService.getUserById(id);

        return ResponseEntity.ok(
                UserResponse.fromUser(user)
        );
    }

    // =========================================================
    // CREATE USER
    // =========================================================

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request
    ) {

        User user =
                adminService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        UserResponse.fromUser(user)
                );
    }

    // =========================================================
    // UPDATE USER
    // =========================================================

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {

        User user =
                adminService.updateUser(
                        id,
                        request
                );

        return ResponseEntity.ok(
                UserResponse.fromUser(user)
        );
    }

    // =========================================================
    // DISABLE USER
    // =========================================================

    @PatchMapping("/users/{id}/disable")
    public ResponseEntity<UserResponse> disableUser(
            @PathVariable Long id
    ) {

        User user =
                adminService.disableUser(id);

        return ResponseEntity.ok(
                UserResponse.fromUser(user)
        );
    }

    // =========================================================
    // GET SYSTEM SETTINGS
    // =========================================================

    @GetMapping("/settings")
    public ResponseEntity<SystemSettingsResponse>
    getSettings() {

        return ResponseEntity.ok(
                adminService.getSettings()
        );
    }

    // =========================================================
    // UPDATE SYSTEM SETTINGS
    // =========================================================

    @PutMapping("/settings")
    public ResponseEntity<SystemSettingsResponse>
    updateSettings(
            @RequestBody SystemSettingsResponse request
    ) {

        return ResponseEntity.ok(
                adminService.updateSettings(request)
        );
    }
}