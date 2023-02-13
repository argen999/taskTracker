package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.CommentServiceImpl;
import com.example.tasktrackerb7.dto.request.CommentRequest;
import com.example.tasktrackerb7.dto.response.CommentResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Comment Api", description = "Comment Api")
public class CommentApi {

    private final CommentServiceImpl commentService;

    @Operation(summary = "Create", description = "Create comment by cardId")
    @PostMapping()
    CommentResponse create(@RequestBody @Valid CommentRequest commentRequest) {
        return commentService.saveComment(commentRequest);
    }

    @Operation(summary = "Update", description = "Update comment")
    @PutMapping("/comment/{id}")
    CommentResponse edit(@RequestBody @Valid CommentRequest commentRequest) {
        return commentService.editComment(commentRequest);
    }

    @Operation(summary = "Delete", description = "Delete comment")
    @DeleteMapping("/delete/{id}")
    SimpleResponse delete(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @Operation(summary = "Get all", description = "Get all comment")
    @GetMapping("/getAll/{id}")
    List<CommentResponse> getAll(@PathVariable Long id) {
        return commentService.getAllComments(id);
    }
}
