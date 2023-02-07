package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.ChecklistService;
import com.example.tasktrackerb7.dto.request.ChecklistRequest;
import com.example.tasktrackerb7.dto.request.UpdateChecklistRequest;
import com.example.tasktrackerb7.dto.response.ChecklistResponse;
import com.example.tasktrackerb7.dto.response.ItemResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository checklistRepository;

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    private final BoardRepository boardRepository;

    private final WorkspaceRepository workspaceRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public ChecklistResponse create(Long id, ChecklistRequest checklistRequest) {
        User user = getAuthenticateUser();
        Card card = cardRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Card with id:" + id + " not found"));
        Board board = boardRepository.findById(card.getColumn().getBoard().getId()).orElseThrow(() ->
                new NotFoundException("Board with id: " + card.getColumn().getBoard().getId() + " not found"));
        Workspace workspace = workspaceRepository.findById(board.getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + board.getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            Checklist checklist = new Checklist();
            checklist.setTitle(checklistRequest.getName());
            checklist.setCard(card);
            card.addChecklist(checklist);
            return convertToResponse(checklistRepository.save(checklist));
        } else {
            throw new BadRequestException("You are not member in this workspace");
        }
    }

    @Override
    public ChecklistResponse update(UpdateChecklistRequest updateChecklistRequest) {
        User user = getAuthenticateUser();
        Checklist checklist = checklistRepository.findById(updateChecklistRequest.getId()).orElseThrow(() ->
                new NotFoundException("Checklist with id:" + updateChecklistRequest.getId() + " not found"));
        Workspace workspace = workspaceRepository.findById(checklist.getCard().getColumn().getBoard().getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + checklist.getCard().getColumn().getBoard().getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            checklist.setTitle(updateChecklistRequest.getName());
            return convertToResponse(checklistRepository.save(checklist));
        } else {
            throw new BadRequestException("You are not member in this workspace");
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();
        Checklist checklist = checklistRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Checklist with id: " + id + " not found!"));
        Workspace workspace = workspaceRepository.findById(checklist.getCard().getColumn().getBoard().getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + checklist.getCard().getColumn().getBoard().getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            for (Item item : checklist.getItems()) {
                item.setChecklist(null);
            }
            checklistRepository.delete(checklist);
            return new SimpleResponse("Checklist with id " + id + " successfully deleted");
        } else {
            throw new BadRequestException("You are not member in this workspace");
        }
    }

    @Override
    public List<ChecklistResponse> findAllChecklistsByCardId(Long id) {
        User user = getAuthenticateUser();
        List<Checklist> checklists = checklistRepository.getAllChecklists(id);
        List<ChecklistResponse> checklistResponses = new ArrayList<>();
        for (Checklist c : checklists) {
            Workspace workspace = workspaceRepository.findById(c.getCard().getColumn().getBoard().getWorkspace().getId()).orElseThrow(() ->
                    new NotFoundException("Workspace with id: " + c.getCard().getColumn().getBoard().getWorkspace().getId() + " not found"));
            if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
                for (Checklist checklist : checklists) {
                    checklistResponses.add(convertToResponse(checklist));
                }
            } else {
                throw new BadRequestException("You are not member in this workspace");
            }
        }
        return checklistResponses;
    }

    public ChecklistResponse convertToResponse(Checklist checklist) {
        List<Item> items = new ArrayList<>();
        if (checklist.getItems() != null) {
            items = checklist.getItems();
        }

        List<ItemResponse> itemResponses = new ArrayList<>();

        int countOfItems = 0;
        int countOfCompletedItems = 0;
        String counts = " ";
        if (items != null) {
            for (Item item : items) {
                countOfItems++;
                if (item.getIsDone()) {
                    countOfCompletedItems++;
                }
                itemResponses.add(new ItemResponse(item.getId(), item.getText(), item.getIsDone()));
            }
            int count;
            if(countOfCompletedItems <= 0) {
                count = 0;
            } else {
                count = (countOfCompletedItems * 100) / countOfItems;
            }
            counts = countOfItems + "/" + countOfCompletedItems;
            checklist.setPercent(count);
            checklistRepository.save(checklist);
        }

        return new ChecklistResponse(checklist.getId(), checklist.getTitle(), counts, checklist.getPercent(), itemResponses);
    }
}
