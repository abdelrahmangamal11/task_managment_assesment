package com.assesment.task_project.mappers;

import com.assesment.task_project.domain.Dto.TaskDto;
import com.assesment.task_project.domain.entities.Task;
import com.assesment.task_project.domain.entities.TaskStatus;

public interface TaskMappers {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
