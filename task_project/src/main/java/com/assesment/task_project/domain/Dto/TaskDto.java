package com.assesment.task_project.domain.Dto;

import com.assesment.task_project.domain.entities.TaskStatus;

import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        TaskStatus status
) {
}
