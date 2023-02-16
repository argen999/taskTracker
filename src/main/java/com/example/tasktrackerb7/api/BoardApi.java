package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.BoardService;
import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.request.BoardUpdateRequest;
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
@RequestMapping("api/boards")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Board Api", description = "Board Api")
public class BoardApi {

    private final BoardService boardServiceImpl;

    @Operation(summary = "Create board", description = "Create new board by workspace id")
    @PostMapping
    public BoardResponse create(@RequestBody BoardRequest boardRequest) {
        return boardServiceImpl.create(boardRequest);
    }

    @Operation(summary = "Update board", description = "Update board(if background = true - update background, if background = false - update name)")
    @PutMapping
    public BoardResponse update(@RequestBody @Valid BoardUpdateRequest boardUpdateRequest) {
        return boardServiceImpl.update(boardUpdateRequest);
    }

    @Operation(summary = "Delete board", description = "Delete board by id")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return boardServiceImpl.delete(id);
    }

    @Operation(summary = "Get all boards", description = "Get all boards by workspace id")
    @GetMapping("/all/{id}")
    public List<BoardResponse> getAll(@PathVariable Long id) {
        return boardServiceImpl.getAllByWorkspaceId(id);
    }

    @Operation(summary = "Get board", description = "Get board by id")
    @GetMapping("/{id}")
    public BoardResponse getById(@PathVariable Long id) {
        return boardServiceImpl.getById(id);
    }
}
