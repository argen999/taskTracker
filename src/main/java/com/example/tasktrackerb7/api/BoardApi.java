package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.BoardService;
import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.request.BoardUpdateRequest;
import com.example.tasktrackerb7.dto.response.BoardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/boards")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Board Api", description = "Board Api")
public class BoardApi {

    private final BoardService boardServiceImpl;

    @Operation(summary = "Create", description = "Create board")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public BoardResponse create(@RequestBody BoardRequest boardRequest) {
        return boardServiceImpl.create(boardRequest);
    }

    @Operation(summary = "Update name", description = "Update board name")
    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public BoardResponse update(@RequestBody @Valid BoardUpdateRequest boardUpdateRequest) {
        return boardServiceImpl.update(boardUpdateRequest);
    }

    @Operation(summary = "Delete", description = "Delete board by board id")
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse delete(@PathVariable Long id) {
        return boardServiceImpl.delete(id);
    }

    @Operation(summary = "Get all", description = "Get all boards")
    @GetMapping("/workspace/{id}")
    @PreAuthorize("isAuthenticated()")
    public List<BoardResponse> getAll(@PathVariable Long id) {
        return boardServiceImpl.getAllByWorkspaceId(id);
    }

    @Operation(summary = "Get by id", description = "Get board by id")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public BoardResponse getById(@PathVariable Long id) {
        return boardServiceImpl.getById(id);
    }
}
