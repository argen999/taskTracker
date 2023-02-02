package com.example.tasktrackerb7.db.service;

public interface EmailSenderService {

    void sendEmail(String to ,
                   String subject,
                   String message);
}
