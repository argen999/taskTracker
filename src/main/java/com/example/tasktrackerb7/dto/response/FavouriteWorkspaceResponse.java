package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteWorkspaceResponse {

    private Long id;

    private String workspaceName;

    public FavouriteWorkspaceResponse(Long id,String workspaceName) {
        this.workspaceName = workspaceName;
        this.id = id;
    }
}
