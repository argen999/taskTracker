package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.WorkspaceServiceImpl;
import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/workspaces")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Workspace api", description = "Workspace Api")
public class WorkspaceApi {

    private final WorkspaceServiceImpl workspaceService;

    @Operation(summary = "Create", description = "Create workspace")
    @PostMapping
    public WorkspaceResponse create(@RequestBody WorkspaceRequest workspaceRequest) {
        return workspaceService.create(workspaceRequest);
    }


}
