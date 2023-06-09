package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceInnerPageResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public interface WorkspaceService {

    WorkspaceResponse create(WorkspaceRequest workspaceRequest) throws MessagingException;

    SimpleResponse delete(Long id);

    WorkspaceResponse update(Long id, WorkspaceRequest workspaceRequest);

    WorkspaceResponse getById(Long id);

    List<WorkspaceResponse> getAll();

    WorkspaceInnerPageResponse getWorkspaceInnerPageById(Long id);

}
