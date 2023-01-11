package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "checklists")
@Getter
@Setter
@NoArgsConstructor
public class Checklist {

    @Id
    @SequenceGenerator(name = "checklist_seq", sequenceName = "checklist_seq", allocationSize = 1)
    @GeneratedValue(generator = "checklist_seq", strategy = GenerationType.SEQUENCE)

    private Long id;

    private String title;

    private String percent;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "checklist")
    private List<Item> items;


}
