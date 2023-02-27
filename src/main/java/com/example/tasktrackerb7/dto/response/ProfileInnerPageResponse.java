package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProfileInnerPageResponse {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private List<WorkspaceResponse> workspaceResponses;

}
