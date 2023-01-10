package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
public class Card {
    @Id
    @SequenceGenerator(name = "card_seq", sequenceName = "card_seq", allocationSize = 1)
    @GeneratedValue(generator = "card_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private boolean archive;
    private String description;
    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Column column;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = EAGER)
    private Estimation estimation;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = EAGER, mappedBy = "cards")
    private List<User> users;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY, mappedBy = "cards")
    private List<Checklist> checklists;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = EAGER, mappedBy = "cards")
    private List<Comment> comments;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
    private List<Attachment> attachments;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
    private List<Label> labels;
}
