package com.example.tasktrackerb7.converter;

import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import com.example.tasktrackerb7.dto.response.ParticipantResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParticipantResponseConverter {
    public ParticipantResponse view(UserWorkspaceRole userWorkspaceRole) {

        if (userWorkspaceRole == null) {
            return null;
        }
        ParticipantResponse participantResponse = new ParticipantResponse();
        participantResponse.setId(userWorkspaceRole.getUser().getId());
        participantResponse.setName(userWorkspaceRole.getUser().getName());
        participantResponse.setSurname(userWorkspaceRole.getUser().getSurname());
        participantResponse.setEmail(userWorkspaceRole.getUser().getEmail());
        participantResponse.setRole(userWorkspaceRole.getRole().getName());
        return participantResponse;
    }

    public List<ParticipantResponse> viewAll(List<UserWorkspaceRole> userWorkspaceRoles) {
        List<ParticipantResponse> participantResponses = new ArrayList<>();
        for (UserWorkspaceRole userWorkspaceRole : userWorkspaceRoles) {
            participantResponses.add(view(userWorkspaceRole));
        }
        return participantResponses;
    }

    public List<ParticipantResponse> viewAllAdmins(List<UserWorkspaceRole> userWorkspaceRoles) {
        List<ParticipantResponse> participantResponses = new ArrayList<>();
        for (UserWorkspaceRole userWorkspaceRole : userWorkspaceRoles) {
            if (userWorkspaceRole.getRole().getName().equals("ADMIN")) {
                participantResponses.add(view(userWorkspaceRole));
            }
        }
        return participantResponses;
    }

    public List<ParticipantResponse> viewAllMembers(List<UserWorkspaceRole> userWorkspaceRoles) {
        List<ParticipantResponse> participantResponses = new ArrayList<>();
        for (UserWorkspaceRole userWorkspaceRole : userWorkspaceRoles) {
            if (userWorkspaceRole.getRole().getName().equals("USER")) {
                participantResponses.add(view(userWorkspaceRole));
            }
        }
                return participantResponses;
    }
}
