package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
            select * from notifications where card_id in
            (select id from cards where column_id in
            (select id from columns where board_id in (select id from boards where workspace_id = :id)))
            """, nativeQuery = true)
    List<Notification> getAllByWorkspaceId(Long id);

    @Query(value = """
            select * from notifications where card_id in
            (select id from cards where column_id in
            (select id from columns where board_id = :id))
            """, nativeQuery = true)
    List<Notification> getAllByBoardId(Long id);


    @Query("select n from Notification n where n.user.id=:id ")
    List<Notification> getAllNotification(Long id);
}