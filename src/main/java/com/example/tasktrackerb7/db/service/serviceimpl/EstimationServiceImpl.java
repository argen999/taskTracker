package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.Card;
import com.example.tasktrackerb7.db.entities.Estimation;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.repository.CardRepository;
import com.example.tasktrackerb7.db.repository.EstimationRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.repository.UserWorkspaceRoleRepository;
import com.example.tasktrackerb7.db.service.EstimationService;
import com.example.tasktrackerb7.dto.request.EstimationRequest;
import com.example.tasktrackerb7.dto.response.EstimationResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
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

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public EstimationResponse createEstimation(EstimationRequest estimationRequest) {
        User user = getAuthenticateUser();

        Card card = cardRepository.findById(estimationRequest.getId()).orElseThrow(() -> new NotFoundException("Card not found!"));

        Estimation estimation;

        if (estimationRequest.getDateOfStart().isBefore(estimationRequest.getDateOfFinish())) {
            estimation = new Estimation(estimationRequest);
        } else throw new BadRequestException("The start date must not be before date finish!");

        if (card.getColumn().getBoard().getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), card.getColumn().getBoard().getWorkspace().getId()))) {

            if (card.getEstimation() == null) {

                card.setEstimation(estimation);
                estimation.setCard(card);
                estimation.setCreator(user);
                estimationRepository.save(estimation);
                cardRepository.save(card);

            } else throw new BadRequestException("This card already has estimation!");

        } else throw new BadRequestException("You are not a member of this workspace");

        return new EstimationResponse(estimation.getId(), estimation.getDateOfStart(), estimation.getDateOfFinish(), estimation.getReminder());
    }

    @Override
    public EstimationResponse updateEstimation(EstimationRequest estimationRequest) {
        User user = getAuthenticateUser();

        Estimation estimation = estimationRepository.findById(estimationRequest.getId()).orElseThrow(() -> new NotFoundException("Estimation not found!"));

        if (estimation.getCard().getColumn().getBoard().getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), estimation.getCard().getColumn().getBoard().getWorkspace().getId()))) {

            if (estimationRequest.getDateOfStart().isBefore(estimationRequest.getDateOfFinish())) {

                estimation.setReminder(estimationRequest.getReminderRequest());
                estimation.setDateOfStart(estimationRequest.getDateOfStart());
                estimation.setDateOfFinish(estimationRequest.getDateOfFinish());
                estimationRepository.save(estimation);

            } else throw new BadRequestException("The start date must not be before date finish!");

        } else throw new BadRequestException("You are not a member of this workspace");

        return new EstimationResponse(estimation.getId(), estimation.getDateOfStart(), estimation.getDateOfFinish(), estimation.getReminder());
    }

    @Override
    public SimpleResponse deleteEstimation(Long id) {
        User user = getAuthenticateUser();
        Estimation estimation = estimationRepository.findById(id).orElseThrow(() -> new NotFoundException("Estimation not found!"));
        Card card = cardRepository.findById(estimation.getCard().getId()).orElseThrow(() -> new NotFoundException("Card not found!"));
        if (estimation.getCard().getColumn().getBoard().getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), estimation.getCard().getColumn().getBoard().getWorkspace().getId()))) {

            SimpleResponse simpleResponse;
            if (card.getUsers().contains(user)) {

                estimationRepository.delete(estimation);
                simpleResponse = new SimpleResponse("Deleted successfully!");

            } else throw new BadRequestException("You are not a member in this card!");

            return simpleResponse;

        } else throw new BadRequestException("You are not a member of this workspace");
    }

}
