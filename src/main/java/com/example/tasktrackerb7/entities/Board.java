package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @SequenceGenerator(name = "board_seq",sequenceName = "board_seq",allocationSize = 1)
    @GeneratedValue(generator = "board_seq",strategy = GenerationType.SEQUENCE)
    private Long boarId;

    private Long id;

    private String name;

    private String background;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private WorkSpace workspace;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = EAGER, mappedBy = "board")
    private List<Column> columns;

    private boolean archive;

    private Favourite favourite;
}
