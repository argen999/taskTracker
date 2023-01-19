package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}