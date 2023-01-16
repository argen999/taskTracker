package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import com.example.tasktrackerb7.dto.request.BoardRequest;
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

    @javax.persistence.Column(length = 1000)
    private String background;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Workspace workspace;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "board")
    private List<Column> columns;

    private boolean archive;

    @ManyToOne
    @JoinColumn(name = "favourite_id")
    private Favourite favourite;

    public Board(BoardRequest boardRequest) {
        this.name = boardRequest.getName();
        this.background = boardRequest.getBackground();
    }

}
