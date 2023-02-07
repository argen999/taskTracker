package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}