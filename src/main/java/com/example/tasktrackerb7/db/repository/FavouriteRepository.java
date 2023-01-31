package com.example.tasktrackerb7.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<com.example.tasktrackerb7.db.entities.Favourite, Long> {

}