package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_generator")
    @SequenceGenerator(name = "board_sequence", sequenceName = "board_sequence", allocationSize = 1)
    private Long boarId;
    private Long id;
    private String name;
    private String background;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private WorkSpace workspace;
    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = EAGER, mappedBy = "board")
    private List<Column> columns;
    private boolean archive;
//    private Favourite favourite;
}
