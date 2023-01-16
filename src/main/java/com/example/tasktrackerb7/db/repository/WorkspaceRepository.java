package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}