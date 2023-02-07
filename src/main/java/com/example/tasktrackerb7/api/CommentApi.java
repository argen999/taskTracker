package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.CommentServiceImpl;
import com.example.tasktrackerb7.dto.request.CommentRequest;
import com.example.tasktrackerb7.dto.response.CommentResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
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
    @PostMapping("/{id}")
    CommentResponse create(@PathVariable Long id, @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.saveComment(id, commentRequest);
    }

    @Operation(summary = "Update", description = "Update comment")
    @PostMapping("/comment/{id}")
    CommentResponse edit(@PathVariable Long id, @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.editComment(id, commentRequest);
    }

    @Operation(summary = "Delete", description = "Delete comment")
    @PostMapping("/delete/{id}")
    SimpleResponse delete(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @Operation(summary = "get all", description = "get all comment")
    @PostMapping("/getAll/{id}")
    List<CommentResponse> getAll(@PathVariable Long id) {
        return commentService.getAllComments(id);
    }
}
