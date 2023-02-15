package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationRequest {

    private String email;
    private String role;
    private String invitationLink;
    private Long workspaceId;
}
