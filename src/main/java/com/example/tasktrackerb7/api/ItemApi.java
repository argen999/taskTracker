package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.ItemServiceImpl;
import com.example.tasktrackerb7.dto.request.ItemRequest;
import com.example.tasktrackerb7.dto.response.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/items")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Item Api", description = "Item Api")
public class ItemApi {

    private final ItemServiceImpl itemService;

    @Operation(summary = "Create", description = "Create item")
    @PostMapping("/{id}")
    public ItemResponse create(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        return itemService.create(id, itemRequest);
    }

    @Operation(summary = "Make done", description = "Make item done or not done")
    @PatchMapping("/{id}")
    public ItemResponse makeDone(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        return itemService.makeDone(id, itemRequest);
    }
}
