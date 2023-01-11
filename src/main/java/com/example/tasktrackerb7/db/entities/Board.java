package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @SequenceGenerator(name = "board_seq", sequenceName = "board_seq", allocationSize = 1)
    @GeneratedValue(generator = "board_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String background;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Workspace workspace;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "board")
    private List<Column> columns;

    private boolean archive;

    @Transient
    private Favourite favourite;
}
