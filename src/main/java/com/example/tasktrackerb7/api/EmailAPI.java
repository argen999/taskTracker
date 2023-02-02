package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.EmailSenderService;
import com.example.tasktrackerb7.resource.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailAPI {
    private final EmailSenderService emailSenderService;

    @PostMapping("/send-email")
    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage) {
        this.emailSenderService.sendEmail(emailMessage.getTo(),
                emailMessage.getSubject(),
                emailMessage.getMessage());
        return ResponseEntity.ok("Message send successfully!");
    }
}
