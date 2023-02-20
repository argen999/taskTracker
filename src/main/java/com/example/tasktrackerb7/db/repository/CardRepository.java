package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Card;
import com.example.tasktrackerb7.db.entities.Column;
import com.example.tasktrackerb7.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}