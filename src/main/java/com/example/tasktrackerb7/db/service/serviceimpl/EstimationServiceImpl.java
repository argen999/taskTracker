package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.configs.schedule.ScheduleConfiguration;
import com.example.tasktrackerb7.db.entities.Card;
import com.example.tasktrackerb7.db.entities.Estimation;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.EstimationService;
import com.example.tasktrackerb7.dto.request.EstimationRequest;
import com.example.tasktrackerb7.dto.response.EstimationResponse;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstimationServiceImpl implements EstimationService {

    private final EstimationRepository estimationRepository;

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final ScheduleConfiguration scheduleConfiguration;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public EstimationResponse createEstimation(Long id, EstimationRequest estimationRequest) {
        User fromUser = getAuthenticateUser();

        Card card = cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Card not found!"));

        User user = userRepository.findById(estimationRequest.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));

        Estimation estimation = new Estimation(estimationRequest);

        if (card.getColumn().getBoard().getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(fromUser.getId(), card.getColumn().getBoard().getWorkspace().getId()))
                && card.getColumn().getBoard().getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), card.getColumn().getBoard().getWorkspace().getId()))) {

            if (card.getEstimation() == null) {

                card.setEstimation(estimation);
                estimation.setCard(card);
                estimation.setCreator(user);
                estimationRepository.save(estimation);
                cardRepository.save(card);

                scheduleConfiguration.estimationWithReminder();

            } else throw new BadRequestException("This card already has estimation!");

        } else throw new BadRequestException("You are not a member of this workspace");

        return new EstimationResponse(estimation.getId(), estimation.getDateOfStart(), estimation.getDateOfFinish());
    }



}
