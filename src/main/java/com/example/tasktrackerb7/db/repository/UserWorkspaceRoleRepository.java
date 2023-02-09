package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkspaceRoleRepository extends JpaRepository<UserWorkspaceRole, Long> {

    @Query("select u from UserWorkspaceRole u where u.user.id=:id and u.workspace.id=:workspaceId")
    UserWorkspaceRole getAllUsersByWorkspaceId(Long id, Long workspaceId);

    @Query("select u from UserWorkspaceRole u where u.workspace.id=:workspaceId")
    List<UserWorkspaceRole> getAllParticipantsByWorkspaceId(Long workspaceId);

}