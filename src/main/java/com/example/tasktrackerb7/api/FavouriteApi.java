package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.FavouriteServiceImpl;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/favourite")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Favourite Api", description = "Favourite Api")
public class FavouriteApi {

    private final FavouriteServiceImpl favouriteServiceImpl;


    @Operation(summary = "make favourite board", description = "make favourite board")
    @PostMapping("/{id}")
    public SimpleResponse makeFavouriteBoard(@PathVariable Long id) {
        return favouriteServiceImpl.makeFavouriteBoard(id);
    }

    @Operation(summary = "make favourite workspace", description = "make favourite workspace")
    @PostMapping("/workspace/{id}")
    public SimpleResponse makeFavouriteWorkspace(@PathVariable Long id) {
        return favouriteServiceImpl.makeFavouriteWorkspace(id);
    }

    @Operation(summary = "get all", description = "get all favourite")
    @GetMapping("/getAll")
    List<FavouriteResponse> getAll() {
        return favouriteServiceImpl.getAllFavourite();
    }

}

