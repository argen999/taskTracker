package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    @GeneratedValue(generator = "comment_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private LocalDate dateOfStart;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "comment")
    private List<User> users;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Card cards;
}
