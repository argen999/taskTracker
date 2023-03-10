package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.response.NotificationResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import net.bytebuddy.build.Plugin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    List<NotificationResponse> getAllNotification();

    NotificationResponse getById(Long id);

    SimpleResponse markAsRead();


}
