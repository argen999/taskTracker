package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import com.example.tasktrackerb7.db.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkspaceRoleRepository extends JpaRepository<UserWorkspaceRole, Long> {

    @Query("select u from UserWorkspaceRole u where u.user.id=:id and u.workspace.id=:workspaceId")
    UserWorkspaceRole findByUserIdAndWorkspaceId(Long id, Long workspaceId);

    @Query(value = "select * from user_workspace_roles where user_id = :id", nativeQuery = true)
    UserWorkspaceRole getUser(Long id);

}