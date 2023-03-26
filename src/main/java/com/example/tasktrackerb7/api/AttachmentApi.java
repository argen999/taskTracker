package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.AttachmentService;
import com.example.tasktrackerb7.dto.request.AttachmentRequest;
import com.example.tasktrackerb7.dto.response.AttachmentResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/allIssues")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Attachment Api", description = "Attachment Api")
public class AttachmentApi {

    private final AttachmentService attachmentService;

    @Operation(summary = "Add attachment", description = "Add attachment in card")
    @PostMapping("/{id}")
    public AttachmentResponse addAttachment(@PathVariable Long id, @RequestBody AttachmentRequest attachmentRequest) {
        return attachmentService.addAttachment(id, attachmentRequest);
    }

    @Operation(summary = "Delete attachment", description = "Delete attachmnet from card")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return attachmentService.delete(id);
    }
}
