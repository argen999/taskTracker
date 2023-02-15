package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @SequenceGenerator(name = "item_gen", sequenceName = "item_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "item_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private Boolean isDone;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Checklist checklist;

}
