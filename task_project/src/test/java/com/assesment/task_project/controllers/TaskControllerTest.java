package com.assesment.task_project.controllers;

import com.assesment.task_project.domain.Dto.TaskDto;
import com.assesment.task_project.domain.entities.Task;
import com.assesment.task_project.domain.entities.TaskStatus;
import com.assesment.task_project.domain.entities.User;
import com.assesment.task_project.mappers.TaskMappers;
import com.assesment.task_project.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMappers taskMappers;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TaskController taskController;

    private User testUser;
    private Task testTask;
    private TaskDto testTaskDto;
    private UUID userId;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        taskId = UUID.randomUUID();
        
        testUser = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();

        testTask = Task.builder()
                .id(taskId)
                .title("Test Task")
                .description("Test Description")
                .taskStatus(TaskStatus.Open)
                .user(testUser)
                .build();

        testTaskDto = new TaskDto(taskId, "Test Task", "Test Description", TaskStatus.Open);
    }

    @Test
    void getTasks_WithValidUserId_ShouldReturnTaskDtos() {
        // Given
        List<Task> tasks = Arrays.asList(testTask);
        
        when(request.getAttribute("userId")).thenReturn(userId);
        when(taskService.findTasksByUserId(userId)).thenReturn(tasks);
        when(taskMappers.toDto(testTask)).thenReturn(testTaskDto);

        // When
        List<TaskDto> result = taskController.getTasks(request);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTaskDto.title(), result.get(0).title());
        
        verify(request).getAttribute("userId");
        verify(taskService).findTasksByUserId(userId);
        verify(taskMappers).toDto(testTask);
    }

    @Test
    void getTask_WithValidIdAndUserId_ShouldReturnTaskDto() {
        // Given
        when(request.getAttribute("userId")).thenReturn(userId);
        when(taskService.findTaskById(taskId, userId)).thenReturn(testTask);
        when(taskMappers.toDto(testTask)).thenReturn(testTaskDto);

        // When
        ResponseEntity<TaskDto> response = taskController.getTask(taskId, request);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testTaskDto.title(), response.getBody().title());
        
        verify(request).getAttribute("userId");
        verify(taskService).findTaskById(taskId, userId);
        verify(taskMappers).toDto(testTask);
    }

    @Test
    void creatTask_WithValidTaskDto_ShouldReturnCreatedTask() {
        // Given
        TaskDto newTaskDto = new TaskDto(null, "New Task", "New Description", null);
        Task newTask = Task.builder()
                .id(null)
                .title("New Task")
                .description("New Description")
                .taskStatus(null)
                .user(null)
                .build();
        
        Task createdTask = Task.builder()
                .id(UUID.randomUUID())
                .title("New Task")
                .description("New Description")
                .taskStatus(TaskStatus.Open)
                .user(testUser)
                .build();
        
        TaskDto createdTaskDto = new TaskDto(createdTask.getId(), "New Task", "New Description", TaskStatus.Open);

        when(request.getAttribute("userId")).thenReturn(userId);
        when(taskMappers.fromDto(newTaskDto)).thenReturn(newTask);
        when(taskService.createTask(newTask, userId)).thenReturn(createdTask);
        when(taskMappers.toDto(createdTask)).thenReturn(createdTaskDto);

        // When
        ResponseEntity<TaskDto> response = taskController.creatTask(newTaskDto, request);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Task", response.getBody().title());
        
        verify(request).getAttribute("userId");
        verify(taskMappers).fromDto(newTaskDto);
        verify(taskService).createTask(newTask, userId);
        verify(taskMappers).toDto(createdTask);
    }

    @Test
    void updateTask_WithValidTaskDto_ShouldReturnUpdatedTask() {
        // Given
        TaskDto updatedTaskDto = new TaskDto(taskId, "Updated Task", "Updated Description", TaskStatus.Done);
        Task updatedTask = Task.builder()
                .id(taskId)
                .title("Updated Task")
                .description("Updated Description")
                .taskStatus(TaskStatus.Done)
                .user(testUser)
                .build();
        
        TaskDto resultTaskDto = new TaskDto(taskId, "Updated Task", "Updated Description", TaskStatus.Done);

        when(taskMappers.fromDto(updatedTaskDto)).thenReturn(updatedTask);
        when(taskService.updatetask(updatedTask, taskId)).thenReturn(updatedTask);
        when(taskMappers.toDto(updatedTask)).thenReturn(resultTaskDto);

        // When
        ResponseEntity<TaskDto> response = taskController.updateTask(updatedTaskDto, taskId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Task", response.getBody().title());
        assertEquals(TaskStatus.Done, response.getBody().status());
        
        verify(taskMappers).fromDto(updatedTaskDto);
        verify(taskService).updatetask(updatedTask, taskId);
        verify(taskMappers).toDto(updatedTask);
    }

    @Test
    void deleteTask_WithValidId_ShouldReturnNoContent() {
        // Given
        doNothing().when(taskService).deleteTask(taskId);

        // When
        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService).deleteTask(taskId);
    }
}
