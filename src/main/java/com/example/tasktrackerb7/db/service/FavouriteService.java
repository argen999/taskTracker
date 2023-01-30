package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.db.entities.Favourite;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavouriteService {

    SimpleResponse makeFavouriteBoard(Long id);

    SimpleResponse makeFavouriteWorkspace(Long id);

    List<FavouriteResponse> getAllFavourite();
}
