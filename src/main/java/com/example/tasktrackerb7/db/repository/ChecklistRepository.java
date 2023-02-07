package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    @Query("select c from Checklist c where c.card.id=:id")
    List<Checklist> getAllChecklists(Long id);
}