package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceInnerPageResponse {

    private String workspaceName;

    private long favoritesCount = 0;

    private int cardsCount = 0;

    private long participantsCount = 0;

    private boolean isAdmin = false;

}
