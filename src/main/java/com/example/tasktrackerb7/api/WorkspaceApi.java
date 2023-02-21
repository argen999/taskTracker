package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.WorkspaceService;
import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceInnerPageResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/workspaces")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Workspace api", description = "Workspace Api")
public class WorkspaceApi {

    private final WorkspaceService workspaceService;

    @Operation(summary = "Create workspace", description = "Create new workspace")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public WorkspaceResponse create(@RequestBody WorkspaceRequest workspaceRequest) throws MessagingException {
        return workspaceService.create(workspaceRequest);
    }

    @Operation(summary = "Delete workspace", description = "Delete workspace by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse delete(@PathVariable Long id) {
        return workspaceService.delete(id);
    }

    @Operation(summary = "Update workspace", description = "Update workspace name by id")
    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public WorkspaceResponse update(@PathVariable Long id, @RequestBody @Valid WorkspaceRequest workspaceRequest) {
        return workspaceService.update(id, workspaceRequest);
    }

    @Operation(summary = "Get all workspaces", description = "Get all workspaces in which the user consists")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<WorkspaceResponse> getAll() {
        return workspaceService.getAll();
    }

    @Operation(summary = "Get workspace by id", description = "Get workspace by id in which the user consists")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public WorkspaceResponse getById(@PathVariable Long id) {
        return workspaceService.getById(id);
    }

    @Operation(summary = "", description = "")
    @GetMapping("/getWorkspaceInnerPage/{id}")
    @PreAuthorize("isAuthenticated()")
    public WorkspaceInnerPageResponse getWorkspaceInnerPageByIdyId(@PathVariable Long id) {
        return workspaceService.getWorkspaceInnerPageById(id);
    }
    
}
