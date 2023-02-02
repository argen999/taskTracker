package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.InvitationRequest;
import com.example.tasktrackerb7.dto.response.ParticipantResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public interface MemberService {
    SimpleResponse inviteMemberToWorkspace(InvitationRequest invitationRequest) throws MessagingException;

    void changeMemberRole(Long roleID, Long memberID, Long workspaceID);

    SimpleResponse deleteMemberByIDFromWorkspace(Long workspaceID, Long memberID);

    List<ParticipantResponse> getAllParticipantsByWorkspaceID(Long workspaceID);

    List<ParticipantResponse> getAllAdminsByWorkspaceID(Long workspaceID);

    List<ParticipantResponse> getAllMembersByWorkspaceID(Long workspaceID);


}
