package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    @GeneratedValue(generator = "item_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private boolean isDone;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Checklist checklist;
}
