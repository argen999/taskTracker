package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_gen", sequenceName = "notification_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "notification_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private boolean status;

    private LocalDateTime dateOfWrite;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private User fromUser;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private User user;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Board board;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Column column;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH})
    private Card card;

}
