package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Estimation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimationRepository extends JpaRepository<Estimation, Long> {
}