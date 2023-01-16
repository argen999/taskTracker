package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select case when count(b)>0 then true else false end from Board b where b.name=:name")
    boolean existsByName(@Param(value = "name") String name);

    @Query("select b from Board b where b.workspace.id = ?1 and b.archive = false")
    List<Board> getAllBoards(Long id);
}