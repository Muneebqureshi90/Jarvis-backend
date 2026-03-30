package com.example.jarvisbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating and updating users.
 * Includes validation constraints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be 2-50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be 2-50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 60, message = "Password must be 8-60 characters")
    private String password;

    @Size(max = 20, message = "Phone must be at most 20 characters")
    private String phone;

    @Size(max = 200, message = "Address must be at most 200 characters")
    private String address;
}
