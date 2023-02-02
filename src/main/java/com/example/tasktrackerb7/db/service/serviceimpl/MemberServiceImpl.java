package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.converter.MemberResponseConverter;
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
import com.example.tasktrackerb7.dto.response.MembersResponse;
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

    private final MemberResponseConverter memberResponseConverter;
    private final RoleRepository roleRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }


    @Override
    public SimpleResponse inviteMemberToWorkspace(InvitationRequest invitationRequest) throws MessagingException {
        User user;
        if (!userRepository.existsByEmail(invitationRequest.getEmail())) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setSubject("Invite member to workspace!");
            mimeMessageHelper.setTo(invitationRequest.getEmail());
            if (invitationRequest.getRole().equals("ADMIN")) {
                mimeMessageHelper.setText(invitationRequest.getInvitationLink() + "/" + invitationRequest.getRole() + "/workspaceID/" + invitationRequest.getWorkspaceID());
            } else if (invitationRequest.getRole().equals("USER")) {
                mimeMessageHelper.setText(invitationRequest.getInvitationLink() + "/" + invitationRequest.getRole() + "/workspaceID/" + invitationRequest.getWorkspaceID());
            }
            javaMailSender.send(mimeMessage);
        } else {
            user = userRepository.findByEmail(invitationRequest.getEmail()).orElseThrow(
                    () -> new NotFoundException("Member with email: " + invitationRequest.getEmail() + " not found!")
            );

            Workspace workspace = workspaceRepository.findById(invitationRequest.getWorkspaceID()).orElseThrow(
                    () -> new NotFoundException("Workspace with ID: " + invitationRequest.getWorkspaceID() + " not found!")
            );
            UserWorkspaceRole userWorkspaceRole = new UserWorkspaceRole(user, workspace, invitationRequest.getRole());
            userWorkspaceRoleRepository.save(userWorkspaceRole);
        }
        return new SimpleResponse("Message send successfully!");
    }

    @Override
    public void changeMemberRole(Long roleID, Long memberID, Long workspaceID) {
        Role role = roleRepository.findById(roleID).orElseThrow(
                () -> new NotFoundException("Role with id " + roleID + " not found!"));
        UserWorkspaceRole userWorkspaceRole = userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(memberID, workspaceID);

        userWorkspaceRole.setRole(role);
        userWorkspaceRoleRepository.save(userWorkspaceRole);

    }

    @Override
    public SimpleResponse deleteMemberByIDFromWorkspace(Long workspaceID, Long memberID) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(workspaceID).orElseThrow(() -> new NotFoundException("Workspace with id " + workspaceID + " not found!"));
        User member = userRepository.findById(memberID).orElseThrow(() -> new NotFoundException("Member with id " + memberID + " not found!"));
        UserWorkspaceRole userWorkspaceRoleRETURN;
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(member.getId(), workspace.getId()))) {
            if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
                UserWorkspaceRole userWorkspaceRole = userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(memberID, workspaceID);
                userWorkspaceRoleRETURN = userWorkspaceRole;
                userWorkspaceRoleRepository.delete(userWorkspaceRole);
            } else {
                throw new BadCredentialsException("You can't delete " + member.getName() + " " + member.getSurname() + " because you are not role ADMIN!");
            }
        } else {
            throw new BadCredentialsException("Member with ID " + memberID + " does not exist from workspace " + workspace.getName());
        }

        if (userWorkspaceRoleRETURN.getRole().getId() == 1) {
            return new SimpleResponse("ADMIN " + member.getName() + " " + member.getSurname() + " deleted successfully from workspace " + workspace.getName());
        } else {
            return new SimpleResponse("USER " + member.getName() + " " + member.getSurname() + " deleted successfully from workspace " + workspace.getName());
        }
    }

    @Override
    public List<ParticipantResponse> getAllParticipantsByWorkspaceID(Long workspaceID) {
        Workspace workspace = workspaceRepository.findById(workspaceID).orElseThrow(
                () -> new NotFoundException("Workspace with ID: "
                        + workspaceID + " not found!")

        );
        List<User> users = new ArrayList<>();
        return memberResponseConverter.viewMembers(users);
    }


    @Override
    public List<ParticipantResponse> getAllAdminsByWorkspaceID(Long workspaceID) {
        return null;
    }

    @Override
    public List<ParticipantResponse> getAllMembersByWorkspaceID(Long workspaceID) {
        return null;
    }
}
