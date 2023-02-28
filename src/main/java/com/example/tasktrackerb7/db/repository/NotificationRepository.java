package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.user.id=:id ")
    List<Notification> getAllNotification(Long id);
}