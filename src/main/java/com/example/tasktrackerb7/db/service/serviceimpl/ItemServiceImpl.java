package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.ItemService;
import com.example.tasktrackerb7.dto.request.ItemRequest;
import com.example.tasktrackerb7.dto.response.ItemResponse;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserRepository userRepository;

    private final ChecklistRepository checklistRepository;

    private final WorkspaceRepository workspaceRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final ItemRepository itemRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public ItemResponse create(Long id, ItemRequest itemRequest) {
        User user = getAuthenticateUser();
        Checklist checklist = checklistRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Checklist with id: " + id + " not found"));
        Workspace workspace = workspaceRepository.findById(checklist.getCard().getColumn().getBoard().getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id : " + checklist.getCard().getColumn().getBoard().getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            Item item = new Item();
            item.setText(itemRequest.getText());
            item.setDone(itemRequest.isDone());
            item.setChecklist(checklist);
            checklist.addItem(item);
            itemRepository.save(item);
            return new ItemResponse(item.getId(), item.getText(), item.isDone());
        } else {
            throw new BadRequestException("You are not member in this workspace");
        }
    }

    @Override
    public ItemResponse makeDone(Long id, ItemRequest itemRequest) {
        User user = getAuthenticateUser();
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Item with id: " + id + " not found"));
        Workspace workspace = workspaceRepository.findById(item.getChecklist().getCard().getColumn().getBoard().getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + item.getChecklist().getCard().getColumn().getBoard().getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            item.setDone(itemRequest.isDone());
            itemRepository.save(item);
            return new ItemResponse(item.getId(), item.getText(), item.isDone());
        } else {
            throw new BadRequestException("You are not member in this workspace");
        }
    }

}
