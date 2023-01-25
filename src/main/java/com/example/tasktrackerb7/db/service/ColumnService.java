package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.ColumnRequest;
import com.example.tasktrackerb7.dto.response.ColumnResponse;

import java.util.List;

public interface ColumnService {
    ColumnResponse create(Long boardId, ColumnRequest columnRequest);

    ColumnResponse update(Long id, ColumnRequest columnRequest);

    ColumnResponse delete(Long id);

    List<ColumnResponse> getAll(Long boardId);
}
