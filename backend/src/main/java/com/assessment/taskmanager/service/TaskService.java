package com.assessment.taskmanager.service;

import com.assessment.taskmanager.dto.TaskDto;
import com.assessment.taskmanager.exception.ResourceNotFoundException;
import com.assessment.taskmanager.model.entity.Project;
import com.assessment.taskmanager.model.entity.Task;
import com.assessment.taskmanager.model.entity.User;
import com.assessment.taskmanager.model.enums.TaskStatus;
import com.assessment.taskmanager.repository.ProjectRepository;
import com.assessment.taskmanager.repository.TaskRepository;
import com.assessment.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Page<TaskDto> getTasks(TaskStatus status, Long projectId, Long userId, String title, Pageable pageable) {
        return taskRepository.searchTasks(status, projectId, userId, title, pageable).map(this::mapToDto);
    }

    public TaskDto createTask(TaskDto dto) {
        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User user = null;
        if (dto.assignedUserId() != null) {
            user = userRepository.findById(dto.assignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        Task task = Task.builder()
                .title(dto.title())
                .description(dto.description())
                .status(dto.status() != null ? dto.status() : TaskStatus.PENDING)
                .project(project)
                .assignedTo(user)
                .build();
        return mapToDto(taskRepository.save(task));
    }

    public TaskDto updateTask(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (dto.status() != null) task.setStatus(dto.status());
        if (dto.title() != null) task.setTitle(dto.title());
        if (dto.description() != null) task.setDescription(dto.description());

        if (dto.assignedUserId() != null) {
            User user = userRepository.findById(dto.assignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            task.setAssignedTo(user);
        }

        return mapToDto(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) throw new ResourceNotFoundException("Task not found");
        taskRepository.deleteById(id);
    }

    private TaskDto mapToDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getProject().getId(), task.getAssignedTo() != null ? task.getAssignedTo().getId() : null);
    }
}