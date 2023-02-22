package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkspaceRequest {

    private String name;

    private List<String> emails;

    private String link;
}
