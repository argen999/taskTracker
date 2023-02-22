package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.WorkspaceService;
import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceInnerPageResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final UserRepository userRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final RoleRepository roleRepository;

    private final FavouriteRepository favouriteRepository;

    private final JavaMailSender mailSender;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new NotFoundException("user not found"));
    }

    @Override
    public WorkspaceResponse create(WorkspaceRequest workspaceRequest) throws MessagingException {
        User user = getAuthenticateUser();
        Workspace workspace = inviteToWorkspace(workspaceRequest);
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

    @Override
    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() ->
                new NotFoundException("workspace with id " + id + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

            favouriteRepository.delete(favouriteRepository.deleteWorkspace(id));
            workspaceRepository.delete(workspace);

        } else throw new BadRequestException("you are not member in workspace with id: " + id);
        return new SimpleResponse("workspace with id: " + id + " deleted successfully");
    }

    @Override
    public WorkspaceResponse update(Long id, WorkspaceRequest workspaceRequest) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() ->
                new NotFoundException("workspace with id: " + id + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

            workspace.setName(workspaceRequest.getName());
            workspaceRepository.save(workspace);

        } else
            throw new BadCredentialsException("you can't do update, because you are not member in workspace with id: " + id);

        return convertToResponse(workspace);
    }

    @Override
    public WorkspaceResponse getById(Long id) {
        User user = getAuthenticateUser();
        Workspace workspace = new Workspace();
        for (Workspace w : user.getWorkspaces()) {
            if (Objects.equals(w.getId(), id)) {
                workspace = w;
            }
        }

        if (!Objects.equals(workspace.getId(), id)) {
            throw new BadCredentialsException("you can't do update, because you are not admin in workspace with id: " + id);
        } else {
            return convertToResponse(workspace);
        }
    }

    @Override
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

    @Override
    public WorkspaceInnerPageResponse getWorkspaceInnerPageById(Long id) {
        User user = getAuthenticateUser();

        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this workspace!"));

        WorkspaceInnerPageResponse workspaceInnerPageResponse = new WorkspaceInnerPageResponse();

        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

            workspaceInnerPageResponse.setWorkspaceName(workspace.getName());
            workspaceInnerPageResponse.setFavoritesCount(workspaceRepository.getCountFavorite(user.getId()));
            workspaceInnerPageResponse.setCardsCount(workspace.getCreator().getCards().size());

            long counter = 0;

            for (UserWorkspaceRole u : workspace.getMembers()) {
                if (u.getRole().getName().equals("ADMIN")) {
                    counter++;
                }
            }

            if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
                workspaceInnerPageResponse.setAdmin(true);
            }

            workspaceInnerPageResponse.setParticipantsCount(workspaceRepository.getCountParticipants(workspace.getId()) - counter);

            return workspaceInnerPageResponse;

        } else throw new BadRequestException("You can't do get, because you are not member in workspace with id: " + id);
    }

    private WorkspaceResponse convertToResponse(Workspace workspace) {
        User user = getAuthenticateUser();
        boolean isFavourite = false;
        if (workspace.getFavourites() != null) {
            for (Favourite favorite : workspace.getFavourites()) {
                if (workspace.getFavourites().contains(favorite)) {
                    isFavourite = true;
                    break;
                }
            }
        }
        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getName(),
                user.getPhotoLink(),
                user.getUsername(),
                isFavourite
        );
    }

    private Workspace inviteToWorkspace(WorkspaceRequest workspaceRequest) {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceRequest.getName());
        if (workspaceRequest.getEmails().isEmpty() || workspaceRequest.getEmails().get(0).equals("") || workspaceRequest.getEmails().get(0).isBlank()) {
            throw new BadRequestException("This email is empty");
        } else {
            for (String email : workspaceRequest.getEmails()) {
                boolean exists = userRepository.existsByEmail(email);
                if (!exists) {
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    simpleMailMessage.setFrom("tasktracker.java7@gmail.com");
                    simpleMailMessage.setTo(email);
                    simpleMailMessage.setSubject("[Task tracker] invitation to my workspace");
                    simpleMailMessage.setText(workspaceRequest.getLink());

                    this.mailSender.send(simpleMailMessage);
                }
            }
        }
        return workspace;
    }

}