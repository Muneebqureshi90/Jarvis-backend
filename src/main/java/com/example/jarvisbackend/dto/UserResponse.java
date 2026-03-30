package com.example.jarvisbackend.dto;

import com.example.jarvisbackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for user data.
 * Excludes sensitive fields like password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer version;

    /**
     * Convert entity to response DTO.
     * This method ensures we never expose the password.
     */
    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .version(user.getVersion())
                .build();
    }
}
