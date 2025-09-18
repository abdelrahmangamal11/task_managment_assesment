package com.assesment.task_project.mappers;

import com.assesment.task_project.domain.Dto.TaskDto;
import com.assesment.task_project.domain.entities.Task;
import com.assesment.task_project.domain.entities.TaskStatus;
import com.assesment.task_project.domain.entities.User;
import com.assesment.task_project.mappers.impl.TaskMappersImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskMappersTest {

    private TaskMappersImpl taskMappers;

    @BeforeEach
    void setUp() {
        taskMappers = new TaskMappersImpl();
    }

    @Test
    void fromDto_WithValidTaskDto_ShouldReturnTask() {
        // Given
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = new TaskDto(taskId, "Test Task", "Test Description", TaskStatus.Open);

        // When
        Task result = taskMappers.fromDto(taskDto);

        // Then
        assertNotNull(result);
        assertEquals(taskDto.id(), result.getId());
        assertEquals(taskDto.title(), result.getTitle());
        assertEquals(taskDto.description(), result.getDescription());
        assertEquals(taskDto.status(), result.getTaskStatus());
        assertNull(result.getUser());
    }

    @Test
    void fromDto_WithNullTaskDto_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> taskMappers.fromDto(null));
    }

    @Test
    void toDto_WithValidTask_ShouldReturnTaskDto() {
        // Given
        UUID taskId = UUID.randomUUID();
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();

        Task task = Task.builder()
                .id(taskId)
                .title("Test Task")
                .description("Test Description")
                .taskStatus(TaskStatus.Open)
                .user(user)
                .build();

        // When
        TaskDto result = taskMappers.toDto(task);

        // Then
        assertNotNull(result);
        assertEquals(task.getId(), result.id());
        assertEquals(task.getTitle(), result.title());
        assertEquals(task.getDescription(), result.description());
        assertEquals(task.getTaskStatus(), result.status());
    }

    @Test
    void toDto_WithNullTask_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> taskMappers.toDto(null));
    }

    @Test
    void fromDto_WithTaskDtoHavingNullFields_ShouldHandleGracefully() {
        // Given
        TaskDto taskDto = new TaskDto(null, null, null, null);

        // When
        Task result = taskMappers.fromDto(taskDto);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getTitle());
        assertNull(result.getDescription());
        assertNull(result.getTaskStatus());
        assertNull(result.getUser());
    }

    @Test
    void toDto_WithTaskHavingNullFields_ShouldHandleGracefully() {
        // Given
        Task task = Task.builder()
                .id(null)
                .title(null)
                .description(null)
                .taskStatus(null)
                .user(null)
                .build();

        // When
        TaskDto result = taskMappers.toDto(task);

        // Then
        assertNotNull(result);
        assertNull(result.id());
        assertNull(result.title());
        assertNull(result.description());
        assertNull(result.status());
    }

    @Test
    void fromDto_WithDifferentTaskStatus_ShouldPreserveStatus() {
        // Given
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = new TaskDto(taskId, "Test Task", "Test Description", TaskStatus.Done);

        // When
        Task result = taskMappers.fromDto(taskDto);

        // Then
        assertNotNull(result);
        assertEquals(TaskStatus.Done, result.getTaskStatus());
    }

    @Test
    void toDto_WithDifferentTaskStatus_ShouldPreserveStatus() {
        // Given
        UUID taskId = UUID.randomUUID();
        Task task = Task.builder()
                .id(taskId)
                .title("Test Task")
                .description("Test Description")
                .taskStatus(TaskStatus.Done)
                .user(null)
                .build();

        // When
        TaskDto result = taskMappers.toDto(task);

        // Then
        assertNotNull(result);
        assertEquals(TaskStatus.Done, result.status());
    }
}
