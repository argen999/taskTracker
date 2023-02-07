package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.WorkspaceService;
import com.example.tasktrackerb7.dto.request.WorkspaceRequest;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/workspaces")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Workspace api", description = "Workspace Api")
public class WorkspaceApi {

    private final WorkspaceService workspaceService;

    @Operation(summary = "Create", description = "Create workspace")
    @PostMapping
    public WorkspaceResponse create(@RequestBody WorkspaceRequest workspaceRequest) {
        return workspaceService.create(workspaceRequest);
    }

    @Operation(summary = "Delete", description = "Delete workspace")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return workspaceService.delete(id);
    }

    @Operation(summary = "Update", description = "Update workspace name")
    @PatchMapping("/{id}")
    public WorkspaceResponse update(@PathVariable Long id, @RequestBody @Valid WorkspaceRequest workspaceRequest) {
        return workspaceService.update(id, workspaceRequest);
    }

    @Operation(summary = "Get all", description = "Get all workspaces in which the user consists")
    @GetMapping
    public List<WorkspaceResponse> getAll() {
        return workspaceService.getAll();
    }

    @Operation(summary = "Get by id", description = "Get workspace by id in which the user consists")
    @GetMapping("/{id}")
    public WorkspaceResponse getById(@PathVariable Long id) {
        return workspaceService.getById(id);
    }


}
