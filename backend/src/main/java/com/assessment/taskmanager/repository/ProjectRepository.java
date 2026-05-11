package com.assessment.taskmanager.repository;
import com.assessment.taskmanager.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}