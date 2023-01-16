package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {

    private String name;

    private String background;

    private Long workspaceId;

}
