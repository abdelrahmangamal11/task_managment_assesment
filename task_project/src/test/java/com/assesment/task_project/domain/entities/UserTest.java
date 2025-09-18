package com.assesment.task_project.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();
    }

    @Test
    void userBuilder_ShouldCreateUserWithAllFields() {
        // Then
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("Test User", user.getName());
        assertNotNull(user.getTasks());
        assertTrue(user.getTasks().isEmpty());
    }

    @Test
    void userEquals_WithSameIdAndEmail_ShouldReturnTrue() {
        // Given
        User sameUser = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("differentPassword")
                .name("Different Name")
                .build();

        // When & Then
        assertEquals(user, sameUser);
        assertEquals(user.hashCode(), sameUser.hashCode());
    }

    @Test
    void userEquals_WithDifferentId_ShouldReturnFalse() {
        // Given
        User differentUser = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();

        // When & Then
        assertNotEquals(user, differentUser);
    }

    @Test
    void userEquals_WithDifferentEmail_ShouldReturnFalse() {
        // Given
        User differentUser = User.builder()
                .id(userId)
                .email("different@example.com")
                .password("password123")
                .name("Test User")
                .build();

        // When & Then
        assertNotEquals(user, differentUser);
    }

    @Test
    void userEquals_WithNull_ShouldReturnFalse() {
        // When & Then
        assertNotEquals(user, null);
    }

    @Test
    void userEquals_WithDifferentClass_ShouldReturnFalse() {
        // When & Then
        assertNotEquals(user, "not a user");
    }

    @Test
    void userToString_ShouldContainAllFields() {
        // When
        String userString = user.toString();

        // Then
        assertTrue(userString.contains(userId.toString()));
        assertTrue(userString.contains("test@example.com"));
        assertTrue(userString.contains("password123"));
        assertTrue(userString.contains("Test User"));
    }
}
