package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
public class Card {

    @Id
    @SequenceGenerator(name = "card_gen", sequenceName = "card_seq", allocationSize = 1)
    @GeneratedValue(generator = "card_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private boolean archive;

    private String description;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.EAGER)
    private Column column;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = FetchType.EAGER)
    private Estimation estimation;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "cards")
    private List<User> users;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Checklist> checklists;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "cards")
    private List<Comment> comments;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Attachment> attachments;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Label> labels;

    @OneToOne(cascade = {DETACH, REFRESH, REMOVE, MERGE}, mappedBy = "card")
    private Notification notification;
}
