package com.assesment.task_project.mappers;

import com.assesment.task_project.domain.Dto.UserDto;
import com.assesment.task_project.domain.entities.User;
import com.assesment.task_project.mappers.impl.UserMappersImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMappersTest {

    private UserMappersImpl userMappers;

    @BeforeEach
    void setUp() {
        userMappers = new UserMappersImpl();
    }

    @Test
    void fromUserToDto_WithValidUser_ShouldReturnUserDto() {
        // Given
        User user = User.builder()
                .id(null)
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();

        // When
        UserDto result = userMappers.fromUserToDto(user);

        // Then
        assertNotNull(result);
        assertEquals(user.getEmail(), result.email());
        assertEquals(user.getPassword(), result.password());
        assertEquals(user.getName(), result.name());
    }

    @Test
    void fromUserToDto_WithNullUser_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> userMappers.fromUserToDto(null));
    }

    @Test
    void fromDtoToUser_WithValidUserDto_ShouldReturnUser() {
        // Given
        UserDto userDto = new UserDto("test@example.com", "password123", "Test User");

        // When
        User result = userMappers.fromDtoToUser(userDto);

        // Then
        assertNotNull(result);
        assertEquals(userDto.email(), result.getEmail());
        assertEquals(userDto.password(), result.getPassword());
        assertEquals(userDto.name(), result.getName());
        assertNull(result.getId());
        assertNull(result.getTasks());
    }

    @Test
    void fromDtoToUser_WithNullUserDto_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> userMappers.fromDtoToUser(null));
    }

    @Test
    void fromUserToDto_WithUserHavingNullFields_ShouldHandleGracefully() {
        // Given
        User user = User.builder()
                .id(null)
                .email(null)
                .password(null)
                .name(null)
                .build();

        // When
        UserDto result = userMappers.fromUserToDto(user);

        // Then
        assertNotNull(result);
        assertNull(result.email());
        assertNull(result.password());
        assertNull(result.name());
    }

    @Test
    void fromDtoToUser_WithUserDtoHavingNullFields_ShouldHandleGracefully() {
        // Given
        UserDto userDto = new UserDto(null, null, null);

        // When
        User result = userMappers.fromDtoToUser(userDto);

        // Then
        assertNotNull(result);
        assertNull(result.getEmail());
        assertNull(result.getPassword());
        assertNull(result.getName());
        assertNull(result.getId());
        assertNull(result.getTasks());
    }
}
