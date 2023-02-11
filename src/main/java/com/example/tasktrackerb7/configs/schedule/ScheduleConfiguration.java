package com.example.tasktrackerb7.configs.schedule;

import com.example.tasktrackerb7.db.entities.Estimation;
import com.example.tasktrackerb7.db.entities.Notification;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.repository.EstimationRepository;
import com.example.tasktrackerb7.db.repository.NotificationRepository;
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
        System.out.println("0000000");
        if (!estimations.isEmpty()) {
            label:
            for (Estimation e : estimations) {
                System.out.println("start");
                if (e.getReminder() == null) {
                    continue;
                }
                System.out.println("11111");
                if (!e.getReminder().equals("none")) {
                    System.out.println("222222");
                    LocalDateTime nowForParse = LocalDateTime.now(ZoneId.of("Asia/Almaty"));
                    LocalDate today = LocalDate.now(ZoneId.of("Asia/Almaty"));
                    LocalTime timeNow = nowForParse.toLocalTime();
                    String[] parseTime = timeNow.toString().split(":");
                    String parsed = today + " " + parseTime[0] + ":" + parseTime[1];
                    LocalDateTime now = parseToLocalDateTime(parsed);
                    System.out.println("333333");
                    assert now != null;

                    if (e.getDateOfStart().isBefore(e.getDateOfFinish())) {
                        System.out.println("444444");
                        Notification notification = new Notification();

                        notification.setFromUser(e.getCreator());
                        notification.setUsers(e.getCard().getUsers());
                        notification.setBoard(e.getCard().getColumn().getBoard());
                        notification.setColumn(e.getCard().getColumn());
                        notification.setCard(e.getCard());
                        notification.setDateOfWrite(LocalDate.now());
                        notification.setDateOfWrite(LocalDate.now(ZoneId.of("Asia/Almaty")));
                        for (User user : e.getCard().getUsers()) {
                            user.addNotification(notification);
                            System.out.println("555555");
                        }
                        System.out.println("666666");
                        e.getCreator().addNotification(notification);
                        e.getCard().setNotification(notification);
                        System.out.println("7777777");
                        switch (e.getReminder()) {
                            case "5 min before" -> {
                                if (e.getDateOfFinish().minusMinutes(5).isEqual(now)) {
                                    notification.setText(": timeout expires in 5 minutes!");
                                    notificationRepository.save(notification);
                                    System.out.println("888888");
                                    break label;
                                }
                            }
                            case "15 min before" -> {
                                if (e.getDateOfFinish().minusMinutes(15).isEqual(e.getDateOfFinish())) {
                                    notification.setText(": timeout expires in 30 minutes!");
                                    notificationRepository.save(notification);
                                    System.out.println("9999999");
                                    break label;
                                }
                            }
                            case "30 min before" -> {
                                if (e.getDateOfFinish().minusMinutes(30).isEqual(e.getDateOfFinish())) {
                                    notification.setText(": timeout expires in 15 minutes!");
                                    notificationRepository.save(notification);
                                    System.out.println("1010101");
                                    break label;
                                }
                            }
                            case "1 hour before" -> {
                                if (e.getDateOfFinish().minusHours(1).isEqual(e.getDateOfFinish())) {
                                    notification.setText(": timeout expires in 1 hour!");
                                    notificationRepository.save(notification);
                                    System.out.println("12121212");
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
