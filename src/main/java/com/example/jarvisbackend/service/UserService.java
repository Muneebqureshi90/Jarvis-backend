package com.example.jarvisbackend.service;

import com.example.jarvisbackend.dto.UserRequest;
import com.example.jarvisbackend.dto.UserResponse;
import com.example.jarvisbackend.entity.User;
import com.example.jarvisbackend.exception.DuplicateResourceException;
import com.example.jarvisbackend.exception.ResourceNotFoundException;
import com.example.jarvisbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User service with business logic.
 * All public methods are transactional.
 * Ensures data consistency and proper error handling.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create a new user.
     * Validates that email and phone don't already exist.
     * Encodes password before saving.
     */
    @Transactional
    public UserResponse createUser(UserRequest request) {
        // Check for duplicates
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User with email '" + request.getEmail() + "' already exists");
        }

        if (request.getPhone() != null && userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("User with phone '" + request.getPhone() + "' already exists");
        }

        // Create and encode password
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    /**
     * Get all users.
     * Returns read-only data, no transaction needed for reads,
     * but we maintain consistency with read-committed isolation.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get user by ID.
     * Throws ResourceNotFoundException if not found.
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return UserResponse.fromEntity(user);
    }

    /**
     * Update an existing user.
     * Validates email uniqueness (excluding current user).
     * Does not allow password updates through this method (use dedicated endpoint if needed).
     */
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        // Check email uniqueness if changed
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User with email '" + request.getEmail() + "' already exists");
        }

        // Check phone uniqueness if provided and changed
        if (request.getPhone() != null 
                && !request.getPhone().equals(user.getPhone()) 
                && userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("User with phone '" + request.getPhone() + "' already exists");
        }

        // Update fields (excluding password for security)
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        User updatedUser = userRepository.save(user);
        return UserResponse.fromEntity(updatedUser);
    }

    /**
     * Delete a user by ID.
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Custom query: Search users by name.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> searchUsersByName(String query) {
        return userRepository.searchByName(query).stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Custom query: Get users created after a specific date.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersCreatedAfter(LocalDateTime date) {
        return userRepository.findUsersCreatedAfter(date).stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
