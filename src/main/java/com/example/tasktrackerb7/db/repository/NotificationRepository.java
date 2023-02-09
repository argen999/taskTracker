package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}