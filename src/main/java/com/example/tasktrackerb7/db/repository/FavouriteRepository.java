package com.example.tasktrackerb7.db.repository;


import com.example.tasktrackerb7.dto.response.FavouriteBoardResponse;
import com.example.tasktrackerb7.dto.response.FavouriteWorkspaceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<com.example.tasktrackerb7.db.entities.Favourite, Long> {

    @Query("select new com.example.tasktrackerb7.dto.response.FavouriteBoardResponse(f.board.name,f.user.photoLink) from Favourite f where f.user.id =:id  and f.isBoard = false ")
    List<FavouriteBoardResponse> getFavouriteByIsBoard(Long id);

    @Query("select new com.example.tasktrackerb7.dto.response.FavouriteWorkspaceResponse(f.workspace.name) from Favourite f where f.user.id =:id  and f.isBoard =true")
    List<FavouriteWorkspaceResponse> getWorkspaceByIsFavourite(Long id);


}