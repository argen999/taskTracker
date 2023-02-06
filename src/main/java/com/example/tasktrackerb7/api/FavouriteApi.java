package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.FavouriteServiceImpl;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/favourite")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Favourite Api", description = "Favourite Api")
public class FavouriteApi {

    private final FavouriteServiceImpl favouriteServiceImpl;


    @Operation(summary = "Make favourite board", description = "Make favourite board")
    @PostMapping("/{id}")
    public SimpleResponse makeFavouriteBoard(@PathVariable Long id) {
        return favouriteServiceImpl.makeFavouriteBoard(id);
    }

    @Operation(summary = "Make favourite workspace", description = "Make favourite workspace")
    @PostMapping("/workspace/{id}")
    public SimpleResponse makeFavouriteWorkspace(@PathVariable Long id) {
        return favouriteServiceImpl.makeFavouriteWorkspace(id);
    }

    @Operation(summary = "Get all", description = "Get all favourite")
    @GetMapping("/getAll")
    FavouriteResponse getAll() {
        return favouriteServiceImpl.getAllFavourite();
    }

}

