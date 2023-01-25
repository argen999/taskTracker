package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.WorkspaceService;
import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return userRepository.findByEmail(login).orElseThrow(() ->
                new NotFoundException("user not found"));
    }

    @Override
    public WorkspaceResponse create(WorkspaceRequest workspaceRequest) {
        User user = getAuthenticateUser();
        Workspace workspace = new Workspace();
        Role role = roleRepository.findById(1L).orElseThrow(() ->
                new NotFoundException("role is not found"));
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
        return convertToResponse(workspace);
    }

    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() ->
                new NotFoundException("workspace with id " + id + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
                workspaceRepository.delete(workspace);
            } else {
                throw new BadCredentialsException("you can't delete workspace with id: "
                        + id + " because you are not admin");
            }
        } else {
            throw new BadRequestException("you are not member in workspace with id: " + id);
        }
        return new SimpleResponse("workspace with id: " + id + " deleted successfully");
    }

    public WorkspaceResponse update(Long id, WorkspaceRequest workspaceRequest) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() ->
            new NotFoundException("workspace with id: " + id + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
                workspace.setName(workspaceRequest.getName());
                workspaceRepository.save(workspace);
            } else {
                throw new BadCredentialsException("you can't do update, because you are not admin in workspace with id: " + id);
            }
        } else {
            throw new BadCredentialsException("you can't do update, because you are not member in workspace with id: " + id);
        }
        return convertToResponse(workspace);
    }

    public WorkspaceResponse getById(Long id) {
        User user = getAuthenticateUser();
        Workspace workspace = new Workspace();
        for (Workspace w : user.getWorkspaces()) {
            if (Objects.equals(w.getId(), id)) {
                workspace = w;
            } else {
                throw new BadCredentialsException("you can't see workspace with id: " + id + ", because you are not a member");
            }
        }
        return convertToResponse(workspace);
    }


    public List<WorkspaceResponse> getAll() {
        User user = getAuthenticateUser();
        List<Workspace> workspaces = new ArrayList<>();
        List<WorkspaceResponse> workspaceResponses = new ArrayList<>();
        for (UserWorkspaceRole userWorkspaceRole : user.getUserWorkspaceRoles()) {
            if (userWorkspaceRole.getUser().equals(user)) {
                workspaces.add(userWorkspaceRole.getWorkspace());
            }
        }
        for (Workspace workspace : workspaces) {
            workspaceResponses.add(convertToResponse(workspace));
        }
        return workspaceResponses;
    }

    private WorkspaceResponse convertToResponse(Workspace workspace) {
        User user = getAuthenticateUser();
        boolean isFavourite = false;
        if (workspace.getFavourites() != null) {
            for (Favourite favorite : user.getFavourites()) {
                if (user.getFavourites().contains(favorite)) {
                    isFavourite = true;
                    break;
                }
            }
        }
        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getName(),
                isFavourite
        );
    }

}
