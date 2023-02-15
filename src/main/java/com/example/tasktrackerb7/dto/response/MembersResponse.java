package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MembersResponse {
    private List<ParticipantResponse> membersWorkspace;
}
