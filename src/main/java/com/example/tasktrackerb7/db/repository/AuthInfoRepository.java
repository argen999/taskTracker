package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.entities.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {
}