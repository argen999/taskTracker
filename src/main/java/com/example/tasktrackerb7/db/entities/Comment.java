package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
    @GeneratedValue(generator = "comment_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private LocalDate dateOfStart;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<User> users;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Card cards;
}
