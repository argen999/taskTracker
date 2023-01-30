package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.FavouriteRequest;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import org.springframework.stereotype.Service;

@Service
public interface FavouriteService {

    FavouriteResponse makaFavouriteBoard(Long id, FavouriteRequest favouriteRequest);

//    SimpleResponse deleteBoardIsFavourite(Long id);
//
//    SimpleResponse deleteWorkspaceIsFavourite(Long id,Long workspaceId);

//    FavouriteResponse makeFavouriteWorkspace(FavouriteRequest favouriteRequest ,Long id);

//    List<FavouriteResponse> getAllFavouriteBoards(Long id);
}
