package com.assesment.task_project.services.impl;

import com.assesment.task_project.domain.entities.Task;
import com.assesment.task_project.domain.entities.TaskStatus;
import com.assesment.task_project.domain.entities.User;
import com.assesment.task_project.repository.TaskRepository;
import com.assesment.task_project.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TaskServiceimpl implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public List<Task> findTasksByUserId(UUID userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public List<Task> findTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findTaskById(UUID id,UUID currentUserId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You are not allowed to access this task");
        }

        return  task;
    }


    @Transactional
    @Override
    public Task createTask(Task task,UUID userId) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already has an ID!");
        }

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title!");
        }

        TaskStatus taskStatus = TaskStatus.Open;

        User user = new User();
        user.setId(userId);
        task.setUser(user);

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                taskStatus,
                user
        );

        return taskRepository.save(taskToSave);
    }


    @Transactional
    @Override
    public Task updatetask(Task task,UUID taskId) {

        if (task.getId() == null) {
            throw new IllegalArgumentException("The task must have an ID");
        }
        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task IDs don't match");
        }

        if (task.getTaskStatus() == null) {
            throw new IllegalArgumentException("Task must have a valid status");
        }

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("The task not found!"));


        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setTaskStatus(task.getTaskStatus());

        return taskRepository.save(existingTask);
    }


    @Transactional
    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
