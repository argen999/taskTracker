package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.ColumnRequest;
import com.example.tasktrackerb7.dto.response.ColumnResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;

public interface ColumnService {
    ColumnResponse create(Long boardId, ColumnRequest columnRequest);

    ColumnResponse update(Long id, ColumnRequest columnRequest);

    SimpleResponse delete(Long id);


}
