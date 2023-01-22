package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.response.BoardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    BoardResponse create(BoardRequest boardRequest);

    BoardResponse update(Long id, BoardRequest boardRequest);

    SimpleResponse delete(Long id);

    List<BoardResponse> getAllByWorkspaceId(Long id);

    BoardResponse getById(Long id);

}
