package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Column, Long> {
}
