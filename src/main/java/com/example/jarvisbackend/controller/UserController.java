package com.example.jarvisbackend.controller;

import com.example.jarvisbackend.dto.UserRequest;
import com.example.jarvisbackend.dto.UserResponse;
import com.example.jarvisbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for User operations.
 * All endpoints are prefixed with /api/users.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * GET /api/users
     * Retrieve all users.
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/{id}
     * Retrieve a specific user by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * POST /api/users
     * Create a new user.
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * PUT /api/users/{id}
     * Update an existing user.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * DELETE /api/users/{id}
     * Delete a user.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/users/search?query={query}
     * Search users by name (first or last).
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String query) {
        List<UserResponse> users = userService.searchUsersByName(query);
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/created-after/{date}
     * Get users created after a specific date.
     * Date format: ISO 8601 (e.g., 2024-01-15T10:30:00)
     */
    @GetMapping("/created-after/{date}")
    public ResponseEntity<List<UserResponse>> getUsersCreatedAfter(
            @PathVariable String date) {
        LocalDateTime parsedDate = LocalDateTime.parse(date);
        List<UserResponse> users = userService.getUsersCreatedAfter(parsedDate);
        return ResponseEntity.ok(users);
    }
}
