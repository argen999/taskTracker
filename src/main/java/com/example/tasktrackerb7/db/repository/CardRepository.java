package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select c from  Card c where c.created between :from and :to and c.workspace.id=:id")
    List<Card> searchCardByCreatedAt(Long id, @Param("from") LocalDate from,
                                    @Param("to") LocalDate to);
}