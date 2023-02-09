package com.example.tasktrackerb7.configs.schedule;

import com.example.tasktrackerb7.db.entities.Estimation;
import com.example.tasktrackerb7.db.entities.Notification;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.enums.Reminder;
import com.example.tasktrackerb7.db.repository.EstimationRepository;
import com.example.tasktrackerb7.db.repository.NotificationRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.exceptions.BadRequestException;
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
                                if (e.getDateOfFinish().minusMinutes(5).isEqual(e.getDateOfFinish()))
                                    notification.setText(": timeout expires in 5 minutes!");
                            }

                            if (e.getReminder().equals(Reminder.R15_MIN_BEFORE)) {
                                if (e.getDateOfFinish().minusMinutes(15).isEqual(e.getDateOfFinish()))
                                    notification.setText(": timeout expires in 30 minutes!");
                            }

                            if (e.getReminder().equals(Reminder.R30_MIN_BEFORE)) {
                                if (e.getDateOfFinish().minusMinutes(30).isEqual(e.getDateOfFinish()))
                                    notification.setText(": timeout expires in 15 minutes!");
                            }

                            if (e.getReminder().equals(Reminder.R1_HOUR_BEFORE)) {
                                if (e.getDateOfFinish().minusHours(1).isEqual(e.getDateOfFinish()))
                                    notification.setText(": timeout expires in 1 hour!");
                            }

                            notification.setFromUser(e.getCreator());
                            notification.setUsers(e.getCard().getUsers());
                            notification.setBoard(e.getCard().getColumn().getBoard());
                            notification.setColumn(e.getCard().getColumn());
                            notification.setCard(e.getCard());
                            notification.setDateOfWrite(LocalDate.now());

                            for (User user : e.getCard().getUsers()) {
                                user.addNotification(notification);
                            }

                            e.getCreator().addNotification(notification);
                            e.getCard().setNotification(notification);

                            notificationRepository.save(notification);

                        }
                    } else throw new BadRequestException("This datetime does not precede the specified datetime!");
                }
            }
        }
    }
}
