package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.ItemRequest;
import com.example.tasktrackerb7.dto.response.ItemResponse;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    ItemResponse create(Long id, ItemRequest itemRequest);

    ItemResponse makeDone(Long id, ItemRequest itemRequest);
}
