package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.BoardServiceImpl;
import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.response.BoardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/board")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Board Api", description = "Board Api")
public class BoardApi {

    private final BoardServiceImpl boardServiceImpl;

    @Operation(summary = "create", description = "create board")
    @PostMapping
    public BoardResponse create(@RequestBody BoardRequest boardRequest) {
        return boardServiceImpl.create(boardRequest);
    }

    @Operation(summary = "update name", description = "update board name")
    @PutMapping("/name/{id}")
    public BoardResponse updateName(@PathVariable Long id, @RequestBody @Valid BoardRequest boardRequest) {
        return boardServiceImpl.updateName(id, boardRequest);
    }

    @Operation(summary = "update background", description = "update board background")
    @PutMapping("/{id}")
    public BoardResponse updateBackground(@PathVariable Long id, @RequestBody @Valid BoardRequest boardRequest) {
        return boardServiceImpl.updateBackground(id, boardRequest);
    }

    @Operation(summary = "delete", description = "delete board by workspace id and board id")
    @DeleteMapping("/{id}/{workspaceId}")
    public SimpleResponse delete(@PathVariable Long id, @PathVariable Long workspaceId) {
        return boardServiceImpl.delete(id, workspaceId);
    }

    @Operation(summary = "get all", description = "get all boards")
    @GetMapping("/workspace/{id}")
    public List<BoardResponse> getAll(@PathVariable Long id) {
        return boardServiceImpl.getAllByWorkspaceId(id);
    }

    @Operation(summary = "get by id", description = "get board by id")
    @GetMapping("/{id}")
    public BoardResponse getById(@PathVariable Long id) {
        return boardServiceImpl.getById(id);
    }

}
