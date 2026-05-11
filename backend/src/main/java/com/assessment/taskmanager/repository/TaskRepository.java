package com.assessment.taskmanager.repository;
import com.assessment.taskmanager.model.entity.Task;
import com.assessment.taskmanager.model.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Search and Filter functionality with pagination
    @Query("SELECT t FROM Task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:projectId IS NULL OR t.project.id = :projectId) AND " +
            "(:userId IS NULL OR t.assignedTo.id = :userId) AND " +
            "(:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<Task> searchTasks(@Param("status") TaskStatus status,
                           @Param("projectId") Long projectId,
                           @Param("userId") Long userId,
                           @Param("title") String title,
                           Pageable pageable);
}