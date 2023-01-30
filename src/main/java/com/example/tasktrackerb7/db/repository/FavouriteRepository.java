package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Favourite;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<com.example.tasktrackerb7.db.entities.Favourite, Long> {

    @Query("select f from Favourite f where f.user.id =:id")
    List<FavouriteResponse> getAllFavourite (Long id);

}