package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
public class Card {

    @Id
    @SequenceGenerator(name = "card_gen", sequenceName = "card_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "card_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private Boolean archive;

    private String description;

    private LocalDate created;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.EAGER)
    private Column column;

    @OneToOne(cascade = ALL, mappedBy = "card")
    private Estimation estimation;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    private List<User> users;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "card")
    private List<Checklist> checklists;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "card")
    private List<Comment> comments;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "card")
    private List<Attachment> attachments;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH}, mappedBy = "card")
    private List<Label> labels;

    @OneToOne(cascade = {DETACH, REFRESH, REMOVE, MERGE}, mappedBy = "card")
    private Notification notification;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE, PERSIST})
    private User creator;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE, PERSIST})
    private Workspace workspace;


    public Card(String title) {
        this.title = title;
    }

    public void addChecklist(Checklist checklist) {
        if (checklists == null) {
            checklists = new ArrayList<>();
        }
        checklists.add(checklist);
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

}