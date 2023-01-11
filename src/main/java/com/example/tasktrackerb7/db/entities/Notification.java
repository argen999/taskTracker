package com.example.tasktrackerb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    @GeneratedValue(generator = "notification_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private boolean status;

    private LocalDate dateOfWrite;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User fromUser;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User userId;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Board board;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Column column;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Card card;
}
