package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Workspace;
import com.example.tasktrackerb7.dto.response.ParticipantResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}