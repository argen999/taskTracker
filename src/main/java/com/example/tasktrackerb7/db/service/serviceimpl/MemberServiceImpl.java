package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.converter.ParticipantResponseConverter;
import com.example.tasktrackerb7.db.entities.Role;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import com.example.tasktrackerb7.db.entities.Workspace;
import com.example.tasktrackerb7.db.repository.RoleRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.repository.UserWorkspaceRoleRepository;
import com.example.tasktrackerb7.db.repository.WorkspaceRepository;
import com.example.tasktrackerb7.db.service.MemberService;
import com.example.tasktrackerb7.dto.request.InvitationRequest;
import com.example.tasktrackerb7.dto.response.ParticipantResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;
    private final JavaMailSender javaMailSender;
    private final RoleRepository roleRepository;
    private final ParticipantResponseConverter participantResponseConverter;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public SimpleResponse inviteMemberToWorkspace(InvitationRequest invitationRequest) throws MessagingException {
        User user;
        Workspace workspace = workspaceRepository.findById(invitationRequest.getWorkspaceId()).orElseThrow(
                () -> new NotFoundException("Workspace with ID: " + invitationRequest.getWorkspaceId() + " not found!")
        );
        if (!userRepository.existsByEmail(invitationRequest.getEmail())) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setSubject("You have been invited to the workspace");
            mimeMessageHelper.setTo(invitationRequest.getEmail());
            if (invitationRequest.getRole().equals("ADMIN")) {
                mimeMessageHelper.setText("Workspace ID : " + invitationRequest.getWorkspaceId() + "\nYour role : " + invitationRequest.getRole() + "\nLink to join : " + "/" + invitationRequest.getInvitationLink() + "/");
            } else if (invitationRequest.getRole().equals("USER")) {
                mimeMessageHelper.setText("Workspace ID : " + invitationRequest.getWorkspaceId() + "\nYour role : " + invitationRequest.getRole() + "\nLink to join : " + "/" + invitationRequest.getInvitationLink() + "/");
            }
            javaMailSender.send(mimeMessage);
        } else {
            user = userRepository.findByEmail(invitationRequest.getEmail()).orElseThrow(
                    () -> new NotFoundException("Member with email: " + invitationRequest.getEmail() + " not found!")
            );

            UserWorkspaceRole userWorkspaceRole = new UserWorkspaceRole(user, workspace, invitationRequest.getRole());
            userWorkspaceRoleRepository.save(userWorkspaceRole);
        }
        return new SimpleResponse("Message send successfully!");
    }

    @Override
    public void changeMemberRole(Long roleId, Long memberId, Long workspaceId) {
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException("Role with id " + roleId + " not found!"));
        UserWorkspaceRole userWorkspaceRole = userWorkspaceRoleRepository.getAllUsersByWorkspaceId(memberId, workspaceId);

        userWorkspaceRole.setRole(role);
        userWorkspaceRoleRepository.save(userWorkspaceRole);
        new SimpleResponse("Role changed successfully!");
    }

    @Override
    public SimpleResponse deleteMemberByIdFromWorkspace(Long workspaceId, Long memberId) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NotFoundException("Workspace with id " + workspaceId + " not found!"));

        User member = userRepository.findById(memberId).orElseThrow(() ->
                new NotFoundException("Member with id " + memberId + " not found!")
        );
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.getAllUsersByWorkspaceId(member.getId(), workspace.getId()))) {

            if (userWorkspaceRoleRepository.getAllUsersByWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
                UserWorkspaceRole userWorkspaceRole = userWorkspaceRoleRepository.
                        getAllUsersByWorkspaceId(memberId, workspaceId);
                userWorkspaceRoleRepository.delete(userWorkspaceRole);
            } else {
                throw new BadCredentialsException("You can't delete "
                        + member.getName() + " " + member.getSurname() + " because you are not role ADMIN!");
            }
        } else {
            throw new BadCredentialsException("Member with ID " + memberId + " not exist in workspace " + workspace.getName());
        }
        return new SimpleResponse(member.getName() + " " + member.getSurname() + " deleted successfully!");
    }

    @Override
    public List<ParticipantResponse> getAllParticipantsByWorkspaceId(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                () -> new NotFoundException("Workspace with ID: "
                        + workspaceId + " not found!")

        );
        List<UserWorkspaceRole> userWorkspaceRoles = new ArrayList<>(userWorkspaceRoleRepository.
                getAllParticipantsByWorkspaceId(workspace.getId()));

        return participantResponseConverter.viewAll(userWorkspaceRoles);
    }

    @Override
    public List<ParticipantResponse> getAllAdminsByWorkspaceId(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                () -> new NotFoundException("Workspace with ID: "
                        + workspaceId + " not found!")

        );
        List<UserWorkspaceRole> userWorkspaceRoles = new ArrayList<>(userWorkspaceRoleRepository.
                getAllParticipantsByWorkspaceId(workspace.getId()));

        return participantResponseConverter.viewAllAdmins(userWorkspaceRoles);
    }

    @Override
    public List<ParticipantResponse> getAllMembersByWorkspaceId(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                () -> new NotFoundException("Workspace with ID: "
                        + workspaceId + " not found!")

        );
        List<UserWorkspaceRole> userWorkspaceRoles = new ArrayList<>(userWorkspaceRoleRepository.
                getAllParticipantsByWorkspaceId(workspace.getId()));

        return participantResponseConverter.viewAllMembers(userWorkspaceRoles);
    }
}
