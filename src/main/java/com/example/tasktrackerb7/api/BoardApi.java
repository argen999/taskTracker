package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.BoardServiceImpl;
import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.response.BoardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/board")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BoardApi {

    private final BoardServiceImpl boardServiceImpl;

    @PostMapping
    public BoardResponse create(@RequestBody BoardRequest boardRequest) {
        return boardServiceImpl.create(boardRequest);
    }

    @PutMapping("/name/{id}")
    public BoardResponse updateName(@PathVariable Long id, @RequestBody @Valid BoardRequest boardRequest) {
        return boardServiceImpl.updateName(id, boardRequest);
    }

    @PutMapping("/{id}")
    public BoardResponse updateBackground(@PathVariable Long id, @RequestBody @Valid BoardRequest boardRequest) {
        return boardServiceImpl.updateBackground(id, boardRequest);
    }

    @DeleteMapping("/{id}/{workspaceId}")
    public SimpleResponse delete(@PathVariable Long id, @PathVariable Long workspaceId) {
        return boardServiceImpl.delete(id, workspaceId);
    }

    @GetMapping("/workspace/{id}")
    public List<BoardResponse> getAll(@PathVariable Long id) {
        return boardServiceImpl.getAllByWorkspaceId(id);
    }

    @GetMapping("/{id}")
    public BoardResponse getById(@PathVariable Long id) {
        return boardServiceImpl.getById(id);
    }

}
