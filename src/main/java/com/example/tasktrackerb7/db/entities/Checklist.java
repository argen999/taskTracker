package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "checklists")
@Getter
@Setter
@NoArgsConstructor
public class Checklist {

    @Id
    @SequenceGenerator(name = "checklist_gen", sequenceName = "checklist_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "checklist_gen", strategy = GenerationType.SEQUENCE)

    private Long id;

    private String title;

    private int percent;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "checklist")
    private List<Item> items;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Card card;

    public Checklist(String name) {
        this.title = name;
    }

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
}
