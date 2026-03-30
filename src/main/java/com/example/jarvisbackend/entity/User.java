package com.example.jarvisbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * User entity representing a system user.
 * Optimistic locking enabled via @Version.
 * Indexes added on email and phone for frequently queried columns.
 */
@Entity
@Table(name = "users",
       indexes = {
           @Index(name = "idx_user_email", columnList = "email", unique = true),
           @Index(name = "idx_user_phone", columnList = "phone")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 20)
    private String phone;

    @Column(length = 200)
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Optimistic locking version field.
     * Prevents concurrent update conflicts.
     */
    @Version
    @Column(name = "version")
    private Integer version;
}
