package com.assessment.taskmanager.dto;

import com.assessment.taskmanager.model.enums.TaskStatus;

public record TaskDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long projectId,
        Long assignedUserId
) {}
