package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    @Query(value = "select count(*) from favourites where user_id = :id", nativeQuery = true)
    long getCountFavorite(Long id);

    @Query(value = "select count(*) from user_workspace_roles where workspace_id = :id", nativeQuery = true)
    long getCountParticipants(Long id);

}