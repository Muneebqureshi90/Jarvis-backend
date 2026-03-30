package com.example.jarvisbackend.repository;

import com.example.jarvisbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User repository with standard JPA operations and custom queries.
 * Includes methods for search/filtering and batch operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email with explicit fetch to avoid N+1 issues.
     * @param email User email address
     * @return Optional containing user if found
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    /**
     * Find users by last name.
     * @param lastName User last name
     * @return List of matching users
     */
    List<User> findByLastName(String lastName);

    /**
     * Find users by phone number.
     * @param phone Phone number
     * @return Optional containing user if found
     */
    Optional<User> findByPhone(String phone);

    /**
     * Search users by first name or last name (case-insensitive).
     * Uses LIKE pattern matching.
     * @param query Search term
     * @return List of matching users
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchByName(@Param("query") String query);

    /**
     * Check if email exists (for duplicate prevention).
     * @param email Email to check
     * @return true if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Check if phone exists (for duplicate prevention).
     * @param phone Phone to check
     * @return true if phone exists
     */
    boolean existsByPhone(String phone);

    /**
     * Find users created after a specific date.
     * @param date Cutoff date
     * @return List of users created after date
     */
    @Query("SELECT u FROM User u WHERE u.createdAt > :date")
    List<User> findUsersCreatedAfter(@Param("date") java.time.LocalDateTime date);
}
