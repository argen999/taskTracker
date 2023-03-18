package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.AttachmentService;
import com.example.tasktrackerb7.dto.request.AttachmentRequest;
import com.example.tasktrackerb7.dto.response.AttachmentResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    private final CardRepository cardRepository;

    private final AttachmentRepository attachmentRepository;

    private final UserRepository userRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final WorkspaceRepository workspaceRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("User not found");
            throw new NotFoundException("User not found");
        });
    }

    @Override
    public AttachmentResponse addAttachment(Long id, AttachmentRequest attachmentRequest) {
        User user = getAuthenticateUser();
        Card card = cardRepository.findById(id).orElseThrow(() -> {
            log.error("Card with id: " + id + " not found");
            throw new NotFoundException("Card with id: " + id + " not found");
        });
        Workspace workspace = workspaceRepository.findById(card.getWorkspace().getId()).orElseThrow(() -> {
            log.error("Workspace with id: " + card.getWorkspace().getId() + " not found");
            throw new NotFoundException("Workspace with id: " + card.getWorkspace().getId() + " not found");
        });
        Attachment attachment = new Attachment();
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            attachment.setAttachment(attachmentRequest.getAttachmentLink());
            attachment.setDateOfStart(LocalDate.now());
            attachment.setCard(card);
            attachmentRepository.save(attachment);
        } else {
            throw new BadRequestException("You are not member in this workspace");
        }
        return new AttachmentResponse(attachment.getId(), attachment.getAttachment(), attachment.getDateOfStart());
    }

    @Override
    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();
        Card card = cardRepository.findById(id).orElseThrow(() -> {
            log.error("Card with id: " + id + " not found");
            throw new NotFoundException("Card with id: " + id + " not found");
        });
        Workspace workspace = workspaceRepository.findById(card.getWorkspace().getId()).orElseThrow(() -> {
            log.error("Workspace with id: " + card.getWorkspace().getId() + " not found");
            throw new NotFoundException("Workspace with id: " + card.getWorkspace().getId() + " not found");
        });
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            attachmentRepository.deleteById(id);
        } else {
            throw new BadRequestException("You are not member in this workspace");
        }
        return new SimpleResponse("Attachment with id: " + id + " deleted successfully");
        }
}
