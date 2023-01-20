package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWorkspaceRoleRepository extends JpaRepository<UserWorkspaceRole, Long> {

    @Query("select u from UserWorkspaceRole u where u.user.id=:id and u.workspace.id=:workspaceId")
    UserWorkspaceRole findByUserIdAndWorkspaceId(Long id, Long workspaceId);

}