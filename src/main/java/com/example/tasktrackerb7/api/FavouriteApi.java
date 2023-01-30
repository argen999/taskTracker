package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.FavouriteServiceImpl;
import com.example.tasktrackerb7.dto.request.FavouriteRequest;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
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


    @Operation(summary = "make favourite board", description = "make favourite board")
    @PostMapping("/{id}")
    public FavouriteResponse makeFavouriteBoard(@PathVariable Long id, @RequestBody FavouriteRequest favouriteRequest) {
        return favouriteServiceImpl.makaFavouriteBoard(id,favouriteRequest);
    }

//    @Operation(summary = "delete favourite", description = "delete favourite board")
//    @DeleteMapping("/{id}")
//    public SimpleResponse deleteBoardFavourite(@PathVariable Long id) {
//        return favouriteServiceImpl.deleteBoardIsFavourite(id);
//    }

//    @Operation(summary = "make favourite", description = "make favourite workspace")
//    @PostMapping("/workspace/{id}")
//    public FavouriteResponse makeFavouriteWorkspace(@PathVariable Long id, @RequestBody FavouriteRequest favouriteRequest) {
//        return favouriteServiceImpl.makeFavouriteWorkspace(favouriteRequest, id);
//    }

//    @Operation(summary = "delete favourite", description = "delete favourite workspace")
//    @DeleteMapping("/{id}/{workspaceId}")
//    public SimpleResponse deleteWorkspaceIsFavourite(@PathVariable Long id, @PathVariable Long workspaceId) {
//        return favouriteServiceImpl.deleteWorkspaceIsFavourite(id, workspaceId);
//    }
}
