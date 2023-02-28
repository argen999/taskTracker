package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private Long notificationId;
    private String fullName;
    private String image;
    private LocalDateTime dateOfWrite;
    private boolean status;
    private String text;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.notificationId = notification.getFromUser().getId();
        this.fullName = notification.getFromUser().getName() + " " + notification.getFromUser().getSurname();
        this.image = notification.getFromUser().getPhotoLink();
        this.dateOfWrite  = notification.getDateOfWrite() ;
        this.status = notification.isStatus();
        this.text = notification.getText();
    }

}
