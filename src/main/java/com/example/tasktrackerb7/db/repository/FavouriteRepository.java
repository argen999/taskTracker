package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @Query(value = """
            select * from favourites where workspace_id = :id or board_id =
            (select b.id from boards b where b.workspace_id = :id);
            """, nativeQuery = true)
    Favourite deleteWorkspace(Long id);
}