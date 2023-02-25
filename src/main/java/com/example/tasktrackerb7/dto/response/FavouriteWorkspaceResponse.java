package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteWorkspaceResponse {

    private Long id;

    private Long workspaceId;

    private String workspaceName;

    public FavouriteWorkspaceResponse(Long id, Long workspaceId,String workspaceName) {
        this.workspaceName = workspaceName;
        this.id = id;
        this.workspaceId = workspaceId;
    }
}
