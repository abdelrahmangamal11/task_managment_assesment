package com.assesment.task_project.controllers;

import com.assesment.task_project.domain.Dto.TaskDto;
import com.assesment.task_project.domain.entities.Task;
import com.assesment.task_project.mappers.TaskMappers;
import com.assesment.task_project.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMappers taskMappers;

    @GetMapping
    public List<TaskDto> getTasks (HttpServletRequest request){
        UUID userId = (UUID) request.getAttribute("userId");
        List<Task> tasks = taskService.findTasksByUserId(userId);
       return  tasks.stream().map(taskMappers::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable UUID id,HttpServletRequest request){
        UUID currentUserId = (UUID) request.getAttribute("userId");
        Task task = taskService.findTaskById(id, currentUserId);
        return ResponseEntity.ok(taskMappers.toDto(task));
    }

    @PostMapping
    public ResponseEntity<TaskDto> creatTask(@RequestBody TaskDto taskDto,HttpServletRequest request){
        UUID userId = (UUID) request.getAttribute("userId");
        Task task=taskMappers.fromDto(taskDto);
        Task createdtask =taskService.createTask(task,userId );
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskMappers.toDto(createdtask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto,
                                              @PathVariable UUID id){
        Task task=taskMappers.fromDto(taskDto);
        Task updatedTask=taskService.updatetask(task,id);

        return ResponseEntity.ok(taskMappers.toDto(updatedTask)) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
