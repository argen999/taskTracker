package com.example.tasktrackerb7.configs.schedule;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.entities.enums.NotificationType;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

    private final NotificationRepository notificationRepository;

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final ColumnRepository columnRepository;

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

    @Transactional
    @Scheduled(cron = "0 0/1 * * * *")
    public void estimationWithReminder() {
        List<Estimation> estimations = estimationRepository.findAll();
        if (!estimations.isEmpty()) {
            label:
            for (Estimation e : estimations) {

                if (!e.getReminder().equals("None")) {

                    LocalDateTime nowForParse = LocalDateTime.now(ZoneId.of("Asia/Almaty"));
                    LocalDate today = LocalDate.now(ZoneId.of("Asia/Almaty"));
                    LocalTime timeNow = nowForParse.toLocalTime();
                    String[] parseTime = timeNow.toString().split(":");
                    String parsed = today + " " + parseTime[0] + ":" + parseTime[1];
                    LocalDateTime now = parseToLocalDateTime(parsed);

                    assert now != null;

                    if (e.getDateOfStart().isBefore(e.getDateOfFinish())) {

                        User creator = userRepository.findById(e.getCreator().getId())
                                .orElseThrow(() -> new NotFoundException("Creator not found!"));

                        Board board = boardRepository.findById(e.getCard().getColumn().getBoard().getId())
                                .orElseThrow(() -> new NotFoundException("Board not found!"));

                        Column column = columnRepository.findById(e.getCard().getColumn().getId())
                                .orElseThrow(() -> new NotFoundException("Column not found!"));

                        Card card = cardRepository.findById(e.getCard().getId())
                                .orElseThrow(() -> new NotFoundException("Card not found!"));

                        switch (e.getReminder()) {
                            case "5 min. before" -> {
                                if (e.getDateOfFinish().minusMinutes(5).isEqual(now)) {
                                    for (User user : userRepository.getAll(card.getId())) {
                                        Notification notification = new Notification();
                                        if (user.equals(creator)) {
                                            creator.addNotification(notification);
                                            continue;
                                        }
                                        notification.setCard(card);
                                        notification.setDateOfWrite(LocalDateTime.from(LocalDate.now(ZoneId.of("Asia/Almaty"))));
                                        notification.setColumn(column);
                                        notification.setBoard(board);
                                        notification.setFromUser(creator);
                                        notification.setText(": timeout expires in 5 minutes!");
                                        user.addNotification(notification);
                                        notification.setUser(user);
                                        notification.setStatus(false);
                                        notification.setNotificationType(NotificationType.REMINDER);
                                        notificationRepository.save(notification);
                                    }

                                    break label;
                                }
                            }

                            case "15 min. before" -> {
                                if (e.getDateOfFinish().minusMinutes(15).isEqual(now)) {
                                    for (User user : userRepository.getAll(card.getId())) {
                                        Notification notification = new Notification();
                                        if (user.equals(creator)) {
                                            creator.addNotification(notification);
                                            continue;
                                        }
                                        notification.setCard(card);
                                        notification.setDateOfWrite(LocalDateTime.from(LocalDate.now(ZoneId.of("Asia/Almaty"))));
                                        notification.setColumn(column);
                                        notification.setBoard(board);
                                        notification.setFromUser(creator);
                                        notification.setText(": timeout expires in 15 minutes!");
                                        user.addNotification(notification);
                                        notification.setUser(user);
                                        notification.setStatus(false);
                                        notification.setNotificationType(NotificationType.REMINDER);
                                        notificationRepository.save(notification);
                                    }
                                    break label;
                                }
                            }

                            case "30 min. before" -> {
                                if (e.getDateOfFinish().minusMinutes(30).isEqual(now)) {
                                    for (User user : userRepository.getAll(card.getId())) {
                                        Notification notification = new Notification();
                                        if (user.equals(creator)) {
                                            creator.addNotification(notification);
                                            continue;
                                        }
                                        notification.setCard(card);
                                        notification.setDateOfWrite(LocalDateTime.from(LocalDate.now(ZoneId.of("Asia/Almaty"))));
                                        notification.setColumn(column);
                                        notification.setBoard(board);
                                        notification.setFromUser(creator);
                                        notification.setText(": timeout expires in 30 minutes!");
                                        user.addNotification(notification);
                                        notification.setUser(user);
                                        notification.setStatus(false);
                                        notification.setNotificationType(NotificationType.REMINDER);
                                        notificationRepository.save(notification);
                                    }
                                    break label;
                                }
                            }

                            case "1 hour before" -> {
                                if (e.getDateOfFinish().minusHours(1).isEqual(now)) {
                                    for (User user : userRepository.getAll(card.getId())) {
                                        Notification notification = new Notification();
                                        if (user.equals(creator)) {
                                            creator.addNotification(notification);
                                            continue;
                                        }
                                        notification.setCard(card);
                                        notification.setDateOfWrite(LocalDateTime.from(LocalDate.now(ZoneId.of("Asia/Almaty"))));
                                        notification.setColumn(column);
                                        notification.setBoard(board);
                                        notification.setFromUser(creator);
                                        notification.setText(": timeout expires in 1 hour!");
                                        user.addNotification(notification);
                                        notification.setUser(user);
                                        notification.setStatus(false);
                                        notification.setNotificationType(NotificationType.REMINDER);
                                        notificationRepository.save(notification);
                                    }
                                    break label;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
