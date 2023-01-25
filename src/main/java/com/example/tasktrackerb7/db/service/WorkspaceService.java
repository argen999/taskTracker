package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import org.springframework.stereotype.Service;

@Service
public interface WorkspaceService {

    WorkspaceResponse create(WorkspaceRequest workspaceRequest);
}
