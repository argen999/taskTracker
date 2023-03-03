package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.repository.NotificationRepository;
import com.example.tasktrackerb7.db.service.NotificationService;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import com.example.tasktrackerb7.dto.response.NotificationResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Notification Api", description = "Notification Api")
public class NotificationApi {

    private final NotificationService notificationService;


    @Operation(summary = "Get all user notifications")
    @GetMapping()
    List<NotificationResponse> getAllNotification() {
        return notificationService.getAllNotification();
    }

    @Operation(summary = "Receive notifications by notification id")
    @GetMapping("/{id}")
    NotificationResponse getById(@PathVariable Long id) {
        return notificationService.getById(id);
    }

    @Operation(summary = "Mark as read", description = "Mark as read")
    @PutMapping()
    SimpleResponse markAsRead() {
        return notificationService.markAsRead();
    }
}
