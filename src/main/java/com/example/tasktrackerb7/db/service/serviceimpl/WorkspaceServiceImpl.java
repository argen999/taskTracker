package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.Role;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import com.example.tasktrackerb7.db.entities.Workspace;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.WorkspaceService;
import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final UserRepository userRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final RoleRepository roleRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Override
    public WorkspaceResponse create(WorkspaceRequest workspaceRequest) {
        User user = getAuthenticateUser();
        Workspace workspace = new Workspace();
        Role role = roleRepository.findById(1L).orElseThrow(() -> new NotFoundException("role is not found"));
        UserWorkspaceRole userWorkspaceRole = new UserWorkspaceRole();
        userWorkspaceRole.setUser(user);
        userWorkspaceRole.setWorkspace(workspace);
        userWorkspaceRole.setRole(role);
        user.addUserWorkspaceRole(userWorkspaceRole);
        workspace.setName(workspaceRequest.getName());
        workspace.setCreator(user);
        workspace.addUserWorkspaceRole(userWorkspaceRole);
        workspaceRepository.save(workspace);
        userWorkspaceRoleRepository.save(userWorkspaceRole);
        return new WorkspaceResponse(workspace.getId(), workspace.getName());
    }

    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new NotFoundException("workspace with id " + id + "not found"));
        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
            workspaceRepository.delete(workspace);
        } else {
            throw new BadCredentialsException("you can't delete this workspace because you are not a admin");
        }
        return new SimpleResponse("workspace deleted successfully");
    }

    public WorkspaceResponse getById(Long id) {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() ->
                new NotFoundException("workspace with id " + id + " not found"));
        return new WorkspaceResponse(workspace.getId(), workspace.getName());
    }

//    public WorkspaceResponse getAll(Long id) {
//        User user = getAuthenticateUser();
//        if ()
//    }


}
