package com.finlogic.taskmanager.repository;

import com.finlogic.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 1. Marks this interface as a Spring repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // This interface extends JpaRepository, inheriting all standard CRUD methods.
    // No need to write any methods here unless you need custom queries!
}
