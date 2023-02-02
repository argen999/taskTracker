package com.example.tasktrackerb7.converter;

import com.example.tasktrackerb7.db.entities.Role;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import com.example.tasktrackerb7.db.repository.RoleRepository;
import com.example.tasktrackerb7.dto.response.ParticipantResponse;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberResponseConverter {
    private final RoleRepository roleRepository;

    public ParticipantResponse view(User user) {
        ParticipantResponse participantResponse = new ParticipantResponse();
        if (user == null) {
            return null;
        }
        participantResponse.setName(user.getName());
        participantResponse.setSurname(user.getSurname());
        participantResponse.setEmail(user.getEmail());
        if (user.getRoles().equals("ADMIN")) {
            participantResponse.setRole(user.getRoles().get(1));
        } else {
            participantResponse.setRole(user.getRoles().get(2));
        }
        return participantResponse;
    }

    public List<ParticipantResponse> viewMembers(List<User> users) {
        List<ParticipantResponse> participantResponses = new ArrayList<>();
        for (User user : users) {
            participantResponses.add(view(user));
        }
        return participantResponses;
    }
}
