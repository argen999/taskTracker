package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardRequest {

    private String name;

    private String background;

    private Long workspaceId;

    private boolean nameOrBackground;
}
