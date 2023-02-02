package com.example.tasktrackerb7.dto.request;

import com.example.tasktrackerb7.db.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationRequest {

    private String email;
    private Role role;
    private String invitationLink;
    private Long workspaceID;

}
