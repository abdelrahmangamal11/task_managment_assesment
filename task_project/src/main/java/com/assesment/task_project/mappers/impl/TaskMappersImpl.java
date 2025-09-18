package com.assesment.task_project.mappers.impl;

import com.assesment.task_project.domain.Dto.TaskDto;
import com.assesment.task_project.domain.entities.Task;
import com.assesment.task_project.mappers.TaskMappers;
import org.springframework.stereotype.Component;

@Component
public class TaskMappersImpl implements TaskMappers {

    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.status(),
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus()
        );
    }
}
