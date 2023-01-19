package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_gen", sequenceName = "notification_seq", allocationSize = 1)
    @GeneratedValue(generator = "notification_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private boolean status;

    private LocalDate dateOfWrite;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private User fromUser;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User user;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Board board;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Column column;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST}, fetch = FetchType.LAZY)
    private Card card;
}
