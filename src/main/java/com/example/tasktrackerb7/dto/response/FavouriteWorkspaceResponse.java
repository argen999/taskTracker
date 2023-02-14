package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteWorkspaceResponse {

    private String workspaceName;

    public FavouriteWorkspaceResponse(String workspaceName) {
        this.workspaceName = workspaceName;
    }
}
