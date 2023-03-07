package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Label;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query("select new com.example.tasktrackerb7.dto.response.LabelResponse(l.id, l.description, l.color) from Label l where l.id = ?1")
    List<LabelResponse> getAllLabelResponse(Long id);

    @Transactional
    @Modifying
    @Query("delete from Label l where l.id = :id")
    void deleteLabelById(Long id);
    
}