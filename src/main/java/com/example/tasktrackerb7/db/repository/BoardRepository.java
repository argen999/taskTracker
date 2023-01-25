package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.workspace.id = ?1 and b.archive = false")
    List<Board> getAllBoards(Long id);
}