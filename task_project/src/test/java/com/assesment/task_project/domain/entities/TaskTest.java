package com.assesment.task_project.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;
    private User user;
    private UUID taskId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();
        userId = UUID.randomUUID();
        
        user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();

        task = Task.builder()
                .id(taskId)
                .title("Test Task")
                .description("Test Description")
                .taskStatus(TaskStatus.Open)
                .user(user)
                .build();
    }

    @Test
    void taskBuilder_ShouldCreateTaskWithAllFields() {
        // Then
        assertNotNull(task);
        assertEquals(taskId, task.getId());
        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals(TaskStatus.Open, task.getTaskStatus());
        assertEquals(user, task.getUser());
    }

    @Test
    void taskEquals_WithSameIdAndTitle_ShouldReturnTrue() {
        // Given
        Task sameTask = Task.builder()
                .id(taskId)
                .title("Test Task")
                .description("Different Description")
                .taskStatus(TaskStatus.Done)
                .user(null)
                .build();

        // When & Then
        assertEquals(task, sameTask);
        assertEquals(task.hashCode(), sameTask.hashCode());
    }

    @Test
    void taskEquals_WithDifferentId_ShouldReturnFalse() {
        // Given
        Task differentTask = Task.builder()
                .id(UUID.randomUUID())
                .title("Test Task")
                .description("Test Description")
                .taskStatus(TaskStatus.Open)
                .user(user)
                .build();

        // When & Then
        assertNotEquals(task, differentTask);
    }

    @Test
    void taskEquals_WithDifferentTitle_ShouldReturnFalse() {
        // Given
        Task differentTask = Task.builder()
                .id(taskId)
                .title("Different Task")
                .description("Test Description")
                .taskStatus(TaskStatus.Open)
                .user(user)
                .build();

        // When & Then
        assertNotEquals(task, differentTask);
    }

    @Test
    void taskEquals_WithNull_ShouldReturnFalse() {
        // When & Then
        assertNotEquals(task, null);
    }

    @Test
    void taskEquals_WithDifferentClass_ShouldReturnFalse() {
        // When & Then
        assertNotEquals(task, "not a task");
    }

    @Test
    void taskToString_ShouldContainAllFields() {
        // When
        String taskString = task.toString();

        // Then
        assertTrue(taskString.contains(taskId.toString()));
        assertTrue(taskString.contains("Test Task"));
        assertTrue(taskString.contains("Test Description"));
        assertTrue(taskString.contains("Open"));
    }

    @Test
    void taskStatus_ShouldHaveCorrectValues() {
        // When & Then
        assertEquals(TaskStatus.Open, TaskStatus.valueOf("Open"));
        assertEquals(TaskStatus.Done, TaskStatus.valueOf("Done"));
        assertEquals(2, TaskStatus.values().length);
    }
}
