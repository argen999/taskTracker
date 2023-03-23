package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.AttachmentRequest;
import com.example.tasktrackerb7.dto.response.AttachmentResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface AttachmentService {

    AttachmentResponse addAttachment(Long id, AttachmentRequest attachmentRequest);

    SimpleResponse delete(Long id);
}
