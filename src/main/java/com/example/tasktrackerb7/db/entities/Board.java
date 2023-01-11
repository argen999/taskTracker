package com.example.tasktrackerb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;

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

    private Favourite favourite;
}
