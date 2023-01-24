package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "favourites")
@Getter
@Setter
@NoArgsConstructor
public class Favourite {

    @Id
    @SequenceGenerator(name = "favourite_gen", sequenceName = "favourite_seq", allocationSize = 1)
    @GeneratedValue(generator = "favourite_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH}, mappedBy = "favourite")
    private List<Workspace> workspaces;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH}, mappedBy = "favourite")
    private List<Board> boards;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User user;

}
