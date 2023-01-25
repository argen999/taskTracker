package com.example.tasktrackerb7.db.entities;

import com.example.tasktrackerb7.dto.request.BoardRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @SequenceGenerator(name = "board_gen", sequenceName = "board_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "board_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @javax.persistence.Column(length = 1000)
    private String background;

    private boolean archive;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Workspace workspace;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, PERSIST, REMOVE}, mappedBy = "board")
    private List<Column> columns;

    @OneToMany(cascade = {DETACH, REFRESH, MERGE}, mappedBy = "board")
    private List<Favourite> favourites;

    public Board(BoardRequest boardRequest) {
        this.name = boardRequest.getName();
        this.background = boardRequest.getBackground();
    }

}
