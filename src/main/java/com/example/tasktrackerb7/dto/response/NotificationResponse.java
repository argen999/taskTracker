package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.Notification;
import com.example.tasktrackerb7.db.entities.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class NotificationResponse {


    private Long notificationId;
    private Long fromUserId;
    private Long userId;
    private Long boardId;
    private Long cardId;
    private String boardName;
    private String columnName;
    private String fullName;
    private String image;
    private LocalDateTime dateOfWrite;
    private boolean status;
    private NotificationType notificationType;
    private String text;

    public NotificationResponse(Notification notification) {
        this.notificationId = notification.getId();
        this.fromUserId = notification.getFromUser().getId();
        this.userId = notification.getUser().getId();
        this.boardId = notification.getBoard().getId();
        this.cardId = notification.getCard().getId();
        this.boardName = notification.getBoard().getName();
        this.columnName =  notification.getColumn().getName();
        this.fullName = notification.getFromUser().getName() + " " + notification.getFromUser().getSurname();
        this.image = notification.getFromUser().getPhotoLink();
        this.dateOfWrite  = notification.getDateOfWrite() ;
        this.status = notification.isStatus();
        this.notificationType = notification.getNotificationType();
        this.text = notification.getText();
    }

}
