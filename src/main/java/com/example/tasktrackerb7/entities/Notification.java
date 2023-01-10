package com.example.tasktrackerb7.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    private String text;

    private boolean status;

    private LocalDate dateOfWrite;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private User fromUser;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private User userId;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Board board;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Column column;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Card card;
}
