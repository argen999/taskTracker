package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.CommentRequest;
import com.example.tasktrackerb7.dto.response.CommentResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentResponse saveComment(Long id, CommentRequest commentRequest);

    CommentResponse editComment(Long id, CommentRequest commentRequest);

    SimpleResponse deleteComment(Long id);

    List<CommentResponse> getAllComments(Long id);



}
