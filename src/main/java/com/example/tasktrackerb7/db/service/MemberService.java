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

    void changeMemberRole(Long roleId, Long memberId, Long workspaceId);

    SimpleResponse deleteMemberByIdFromWorkspace(Long workspaceId, Long memberId);

    List<ParticipantResponse> getAllParticipantsByWorkspaceId(Long workspaceId);

    List<ParticipantResponse> getAllAdminsByWorkspaceId(Long workspaceId);

    List<ParticipantResponse> getAllMembersByWorkspaceId(Long workspaceId);


}
