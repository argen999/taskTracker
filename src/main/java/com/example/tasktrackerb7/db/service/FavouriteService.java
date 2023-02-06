package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface FavouriteService {

    SimpleResponse makeFavouriteBoard(Long id);

    SimpleResponse makeFavouriteWorkspace(Long id);

     FavouriteResponse getAllFavourite();
}
