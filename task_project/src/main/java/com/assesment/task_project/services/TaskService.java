package com.assesment.task_project.services;


import com.assesment.task_project.domain.Dto.TaskDto;
import com.assesment.task_project.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> findTasksByUserId(UUID userId);
    List<Task> findTasks();
    Task findTaskById(UUID id,UUID currentUserId);
    Task createTask(Task task,UUID userId);
    Task updatetask(Task task,UUID id);
    void deleteTask(UUID id);
}
