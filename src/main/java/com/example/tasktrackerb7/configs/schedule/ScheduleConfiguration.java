package com.example.tasktrackerb7.configs.schedule;

import com.example.tasktrackerb7.db.entities.Card;
import com.example.tasktrackerb7.db.entities.Estimation;
import com.example.tasktrackerb7.db.entities.Notification;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.enums.Reminder;
import com.example.tasktrackerb7.db.repository.EstimationRepository;
import com.example.tasktrackerb7.db.repository.NotificationRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfiguration {

    private final EstimationRepository estimationRepository;

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    private LocalDate parse(String value) {
        try {
            return DATE_FORMATTER.parse(value, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private LocalDateTime parseToLocalDateTime(String value) {
        try {
            return DATE_TIME_FORMATTER.parse(value, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Transactional
    @Scheduled(cron = "0 0/1 * * * *")
    public void estimationWithReminder() {
        User user = getAuthenticateUser();
        List<Estimation> estimations = estimationRepository.findAll();
        if (!estimations.isEmpty()) {
            for (Estimation e : estimations) {
                if (!e.getReminder().equals(Reminder.NONE)) {
                    LocalDateTime nowForParse = LocalDateTime.now(ZoneId.of("Asia/Almaty"));
                    LocalDate today = LocalDate.now(ZoneId.of("Asia/Almaty"));
                    LocalTime timeNow = nowForParse.toLocalTime();
                    String[] parseTime = timeNow.toString().split(":");
                    String parsed = today + " " + parseTime[0] + ":" + parseTime[1];
                    LocalDateTime now = parseToLocalDateTime(parsed);

                    assert now != null;

                    if (e.getDateOfStart().isBefore(e.getDateOfFinish())) {

                        if (now.equals(e.getDateOfFinish())) {
                            Notification notification = new Notification();

                            notification.setDateOfWrite(LocalDate.now(ZoneId.of("Asia/Almaty")));

                            if (e.getReminder().equals(Reminder.R5_MIN_BEFORE)) {
                                notification.setText(": timeout expires in 5 minutes!");
                            }
                            if (e.getReminder().equals(Reminder.R15_MIN_BEFORE)) {
                                notification.setText(": timeout expires in 30 minutes!");
                            }
                            if (e.getReminder().equals(Reminder.R30_MIN_BEFORE)) {
                                notification.setText(": timeout expires in 1 hour!");
                            }
                            if (e.getReminder().equals(Reminder.R1_HOUR_BEFORE)) {
                                notification.setText(": timeout expires in 1 hour!");
                            }

                            for (Card card : user.getCards()) {
                                if (card.getEstimation().equals(e)) {
                                    notification.setUser(user);
//                                    notification.setFromUser(fromUser);
                                    notification.setBoard(card.getColumn().getBoard());
                                    notification.setColumn(card.getColumn());
                                    notification.setCard(card);
                                    notification.setDateOfWrite(LocalDate.now());
                                }
                            }

//                        fromUser.addNotification(notification);
//                        user.addNotification(notification);
//                        card.setNotification(notification);

                            notificationRepository.save(notification);

                        } else throw new BadRequestException("This datetime does not precede the specified datetime!");
                    }
                }
            }
        }
    }
}
