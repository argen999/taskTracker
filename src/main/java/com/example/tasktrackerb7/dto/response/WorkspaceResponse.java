package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkspaceResponse {

    private Long id;

    private String name;

    private String leadPhotoLink;

    private String leadName;

    private boolean isFavourite;

    public WorkspaceResponse(Long id, String name, String leadPhotoLink, String leadName) {
        this.id = id;
        this.name = name;
        this.leadPhotoLink = leadPhotoLink;
        this.leadName = leadName;
    }
}
