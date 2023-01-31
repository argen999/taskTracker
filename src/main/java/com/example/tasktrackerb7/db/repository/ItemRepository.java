package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}